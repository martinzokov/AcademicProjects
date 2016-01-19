class Order < ActiveRecord::Base
  validates :name, :address, :email, presence: true
  has_many :line_items, dependent: :destroy

  def add_line_items_from_basket(basket)
    basket.line_items.each do |item|
      item.basket_id = nil
      line_items << item
    end
  end

  def get_hash
    {name: self.name, address: self.address, email: self.email}
  end
end
