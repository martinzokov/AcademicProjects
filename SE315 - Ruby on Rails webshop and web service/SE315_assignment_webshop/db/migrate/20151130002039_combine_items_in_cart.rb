class CombineItemsInCart < ActiveRecord::Migration
  def change
  end

  def up
    Basket.all.each do |basket|
      wine_sums = basket.line_items.group(:wine_id).sum(:quantity)

      wine_sums.each do |wine_id, quantity|
        if quantity > 1
          basket.line_items.where(wine_id: wine_id).delete_all

          basket.line_items.create(wine_id: wine_id, quantity: quantity)
        end
      end
    end
  end

  def down
    LineItem.where("quantity>1").each do |line_item|
      line_item.quantity.times do
        LineItem.create basket_id: line_item.basket_id,
            wine_id: line_item.wine_id, quantity: 1
      end
      line_item.destroy
    end
  end

end
