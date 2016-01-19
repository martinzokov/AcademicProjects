require 'test_helper'

class OrdersControllerTest < ActionController::TestCase
  setup do
    @order = orders(:order_one)
  end

  test "should get index" do
    get :index
    assert_redirected_to wines_url
    assert_not_nil assigns(:orders)
  end

  test "should get new" do
    basket = Basket.create
    session[:basket_id] = basket.id
    LineItem.create(basket: basket, wine: wines(:red_wine))

    get :new
    assert_response :success
  end

  test "should create order" do

    assert_difference('Order.count') do
      post :create, order: { address: @order.address, email: @order.email, name: @order.name }
    end

    assert_redirected_to order_path(assigns(:order))
  end


  test "requires item in cart" do
    get :new
    assert_redirected_to wines_url
    assert_equal flash[:notice], "The basket is empty"
  end
end
