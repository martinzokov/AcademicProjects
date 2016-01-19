class OrdersController < ApplicationController
  before_action :set_order, only: [:show, :edit, :update, :destroy]
  before_action :init_suppliers, only: [:create]
  before_action :authenticate_user!, only: [:create, :new]

  # GET /orders
  # GET /orders.json
  def index
    redirect_to wines_url
  end

  # GET /orders/1
  # GET /orders/1.json
  def show
    redirect_to wines_url
  end

  # GET /orders/new
  def new
    @basket = current_basket

    #send order to web service

    if @basket.line_items.empty?
      redirect_to wines_url, notice: "The basket is empty"
      return
    end
    @order = Order.new

    respond_to do |format|
      format.html
      format.json {render json: @order}
    end
  end

  # GET /orders/1/edit
  def edit
  end

  # POST /orders
  # POST /orders.json
  def create

    order_supplier_one = {}
    order_contents_supplier_one = {}
    order_supplier_two = {}
    order_contents_supplier_two = {}

    current_basket.line_items.each do |item|
      temp_wine = Wine.find_by_id(item.wine_id)
      if temp_wine[:supplier] == "DistastefulNonAlcoholicWine co."
        if order_supplier_one.empty? && order_contents_supplier_one.empty?
          order_supplier_one = Order.new(order_params).get_hash
          order_contents_supplier_one.update item.get_hash
        else
          order_contents_supplier_one.update item.get_hash
        end
      end

      if temp_wine[:supplier] == "NobodyLikesWineWithoutAlcohol ltd."
        if order_supplier_two.empty? && order_contents_supplier_two.empty?
          order_supplier_two = Order.new(order_params).get_hash
          order_contents_supplier_two.update item.get_hash
        else
          order_contents_supplier_two.update item.get_hash
        end

      end
    end

    @order = Order.new(order_params)
    @order.add_line_items_from_basket(current_basket)

    unless order_supplier_one.empty?
      HTTParty.post("#{@supplier_one}/orders.json", query: order_supplier_one)
      HTTParty.post("#{@supplier_one}/line_items.json", query: order_contents_supplier_one)
    end
    unless order_supplier_two.empty?
      HTTParty.post("#{@supplier_two}/orders.json", query: order_supplier_two)
      HTTParty.post("#{@supplier_two}/line_items.json", query: order_contents_supplier_two)
    end


    respond_to do |format|
      if @order.save
        Basket.destroy(session[:basket_id])
        session[:basket_id] = nil

        format.html { redirect_to wines_url, notice: "Your order has been placed. Thank you for shopping!"}
        format.json { render :show, status: :created, location: @order }
      else
        @basket = current_basket
        format.html { render :new}
        format.json { render json: @order.errors, status: :unprocessable_entity }
      end
    end

  end


  # PATCH/PUT /orders/1
  # PATCH/PUT /orders/1.json
  def update
    respond_to do |format|
      if @order.update(order_params)
        format.html { redirect_to @order, notice: 'Order was successfully updated.' }
        format.json { render :show, status: :ok, location: @order }
      else
        format.html { render :edit }
        format.json { render json: @order.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /orders/1
  # DELETE /orders/1.json
  def destroy
    @order.destroy
    respond_to do |format|
      format.html { redirect_to orders_url, notice: 'Order was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private


  # Use callbacks to share common setup or constraints between actions.
  def set_order
    @order = Order.find(params[:id])
  end

  # Never trust parameters from the scary internet, only allow the white list through.
  def order_params
    params.require(:order).permit(:name, :address, :email)
  end
end
