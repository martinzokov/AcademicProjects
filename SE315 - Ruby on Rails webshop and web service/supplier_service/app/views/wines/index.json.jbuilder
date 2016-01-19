json.array!(@wines) do |wine|
  json.extract! wine, :productName, :longDesc, :origin, :grapeType, :vegetarian, :bottleSize, :supplier, :price, :image_url
  json.url wine_url(wine, format: :json)
end
