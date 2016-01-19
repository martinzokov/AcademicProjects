class Wine < ActiveRecord::Base
  validates :productName, :longDesc, :origin, :grapeType, :bottleSize, :supplier, :price, presence:true
  validates :price, numericality: {greater_than_or_equal_to: 0.01}
  validates :image_url, allow_blank: true, format:{
                          with: %r{\.(gif|jpg|png)\z}i,
                          message: 'must be a URL for GIF, JPG, PNG'
                      }

  has_many :line_items
  before_destroy :verify_no_line_item_references

  def self.like(query)
    if query.nil?
      []
    else
      where('productName LIKE :query', query: "%#{query}%")
    end
  end

  private

  def verify_no_line_item_references
    if line_items.empty?
      return true
    else
      errors.add(:base, "Existing Line items")
      return false
    end
  end
end
