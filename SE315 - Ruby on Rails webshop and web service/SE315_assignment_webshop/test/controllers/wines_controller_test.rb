require 'test_helper'

class WinesControllerTest < ActionController::TestCase
  setup do
    @wine = wines(:red_wine)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_select '.wine_list'
    assert_select 'h3', 'RedWineTest'
  end

  test "should_not get new" do
    get :new
    assert_redirected_to wines_url
  end

  test "should create wine" do
    assert_difference('Wine.count') do
      post :create, wine: { bottleSize: @wine.bottleSize, grapeType: @wine.grapeType, image_url: @wine.image_url, longDesc: @wine.longDesc, origin: @wine.origin, price: @wine.price, productName: @wine.productName, supplier: @wine.supplier, vegetarian: @wine.vegetarian }
    end

    assert_redirected_to wine_path(assigns(:wine))
  end

  test "should show wine" do
    get :show, id: @wine
    assert_response :success
    assert_select '.wine_information'
    assert_select 'h1', 'RedWineTest'
  end

  test "should_not get edit" do
    get :edit, id: @wine
    assert_redirected_to @wine
  end

  test "should update wine" do
    patch :update, id: @wine, wine: { bottleSize: @wine.bottleSize, grapeType: @wine.grapeType, image_url: @wine.image_url, longDesc: @wine.longDesc, origin: @wine.origin, price: @wine.price, productName: @wine.productName, supplier: @wine.supplier, vegetarian: @wine.vegetarian }
    assert_redirected_to wine_path(assigns(:wine))
  end

  test "should_not destroy wine" do

    delete :destroy, id: @wine

    assert_redirected_to wines_url
  end

  test "search_item" do
    get :search, {query: wines(:white_wine).productName}
    assert_response :success
    assert_select '.wine_item', maximum: 1
    assert_select 'h3', 'WhiteWineTest'
  end

end
