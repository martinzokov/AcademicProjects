
class WinesController < ApplicationController
  before_action :set_wine, only: [ :edit, :update, :destroy]
  before_action :init_suppliers, only: [:index]


  # GET /wines
  # GET /wines.json
  def index

    begin
      response_one = HTTParty.get("#{@supplier_one}/wines.json")
      supplier_one_wines = ActiveSupport::JSON.decode(response_one.body)

      supplier_two_wines = ActiveSupport::JSON.decode(HTTParty.get("#{@supplier_two}/wines.json").body)
      best_wines = select_cheapest_wines(supplier_one_wines, supplier_two_wines)

      best_wines.each do |wine|
        wine.delete('url')
      end

      choose_database_updates(best_wines)
    rescue Errno::ECONNREFUSED
      #recover if there is no connection with supplier
      logger.error "No connection with supplier services"
    end

    @wines = Wine.order(:productName).paginate page: params[:page], per_page: 10

    @basket = current_basket
  end


  def search
    @wines = Wine.like(params[:query]).paginate(page: params[:page], per_page: 10).order('productName')
    @basket = current_basket
    render 'index'
  end

  # GET /wines/1
  # GET /wines/1.json
  def show
    begin
      @wine = Wine.find(params[:id])
      @basket = current_basket
      @line_item = LineItem.new(quantity: 1)
    rescue ActiveRecord::RecordNotFound
      logger.error "Invalid wine with id #{params[:id]}"
      redirect_to wines_url, notice: 'Oops! The wine is invalid'
    else
      respond_to do |format|
        format.html
        format.json { render json: @basket}
      end
    end
  end

  # GET /wines/new
  def new
    redirect_to wines_url
  end

  # GET /wines/1/edit
  def edit
    redirect_to @wine
  end

  # POST /wines
  # POST /wines.json
  def create
    @wine = Wine.new(wine_params)

    respond_to do |format|
      if @wine.save
        format.html { redirect_to @wine, notice: 'Wine was successfully created.' }
        format.json { render :show, status: :created, location: @wine }
      else
        format.html { render :new }
        format.json { render json: @wine.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /wines/1
  # PATCH/PUT /wines/1.json
  def update
    respond_to do |format|
      if @wine.update(wine_params)
        format.html { redirect_to @wine, notice: 'Wine was successfully updated.' }
        format.json { render :show, status: :ok, location: @wine }
      else
        format.html { render :edit }
        format.json { render json: @wine.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /wines/1
  # DELETE /wines/1.json
  def destroy
    redirect_to wines_url
  end

  private

  # Based on a wine_list chooses which entries to update if
  # an item with a better price was found
  def choose_database_updates(wine_list)
    wine_list.each do |wine_entry|
      wine_query = Wine.find_by_productName wine_entry["productName"]
      if !wine_query.nil?
        if wine_query[:supplier] != wine_entry["supplier"]
          wine_query.update(wine_entry)
        end
      else
        Wine.create wine_entry
      end
    end
  end


  # Given two lists of items from the two suppliers, choose
  # the wines that are cheapest
  def select_cheapest_wines(supplier_one_wines, supplier_two_wines)
    cheapest = Array.new
    #assign the first list to the cheapest list to use as a baseline
    cheapest = supplier_one_wines
    supplier_two_wines.each do |other_list_entry|
      if contains? cheapest, other_list_entry
        temp_item = find_item_by_name(cheapest,other_list_entry)
        if temp_item["price"].to_f > other_list_entry["price"].to_f
          remove_by_name(cheapest, temp_item)
          cheapest << other_list_entry
        end
      else
        cheapest << other_list_entry
      end
    end
    return cheapest
  end

  # Finds search_item in list and returns it
  def find_item_by_name(list, search_item)
    found_entry = {}
    list.each do |current_item|
      if current_item["productName"] == search_item["productName"]
        found_entry=current_item
      end
    end
    return found_entry
  end

  # Looks for search_item in list and returns true if found
  def contains?(list, search_item)
    found = false
    list.each do |current_item|
      if current_item["productName"] == search_item["productName"]
        found = true
      end
    end
    return found
  end

  # Removes search_item in list
  def remove_by_name(list, item_to_remove)
    list.each do |current_item|
      if current_item["productName"] == item_to_remove["productName"]
        list.delete current_item
      end
    end
  end


  # Use callbacks to share common setup or constraints between actions.
    def set_wine
      @wine = Wine.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def wine_params
      params.require(:wine).permit(:productName, :longDesc, :origin, :grapeType, :vegetarian, :bottleSize, :supplier, :price, :image_url)
    end
end
