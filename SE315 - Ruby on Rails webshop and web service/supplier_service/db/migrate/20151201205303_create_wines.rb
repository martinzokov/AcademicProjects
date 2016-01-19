class CreateWines < ActiveRecord::Migration
  def change
    create_table :wines do |t|
      t.string :productName
      t.string :longDesc
      t.string :origin
      t.string :grapeType
      t.boolean :vegetarian, default: false
      t.integer :bottleSize
      t.string :supplier
      t.decimal :price
      t.string :image_url, default: "wine_clash.png"

      t.timestamps null: false
    end
  end
end
