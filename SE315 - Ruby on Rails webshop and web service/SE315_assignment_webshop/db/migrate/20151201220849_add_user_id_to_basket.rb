class AddUserIdToBasket < ActiveRecord::Migration
  def change
    add_column :baskets, :user_id, :integer
    add_column
  end
end
