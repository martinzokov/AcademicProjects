require 'test_helper'

class LineItemsControllerTest < ActionController::TestCase
  setup do
    @line_item = line_items(:line_item_one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:line_items)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should add_to_basket" do
    assert_difference('LineItem.count') do
      post :create, {line_item: {wine_id: wines(:red_wine).id, quantity: 2} }
    end
    assert_redirected_to wines_url
    assert_select '#basket'
  end

  test "should show line_item" do
    get :show, id: @line_item
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @line_item
    assert_redirected_to store_url
  end

  test "should update line_item" do
    patch :update, id: @line_item, line_item: { basket_id: @line_item.basket_id, wine_id: @line_item.wine_id }
    assert_redirected_to line_item_path(assigns(:line_item))
  end

  test "should destroy line_item" do
    delete :destroy, id: @line_item


    assert_redirected_to store_url
  end
end
