class Basket < ActiveRecord::Base
  has_many :line_items, dependent: :destroy
  belongs_to :user

  def add_wine(wine_id,quantity)
    current_item = line_items.find_by_wine_id(wine_id)

    if current_item
      current_item.quantity += quantity.to_i
    else
      current_item = line_items.build(wine_id: wine_id, quantity: quantity.to_i)
    end
    current_item
  end

  def total_price
    line_items.to_a.sum {|item| item.total_price}
  end

end
