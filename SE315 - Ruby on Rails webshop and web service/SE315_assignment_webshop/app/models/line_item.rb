class LineItem < ActiveRecord::Base
  belongs_to :order
  belongs_to :wine
  belongs_to :basket

  def total_price
    wine.price * quantity
  end

  def get_hash
    {wine_id: self.wine_id, quantity: self.quantity}
  end
end
