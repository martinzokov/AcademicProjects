# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)
#

# # #supplier1
# for i in 1...10 do
# Wine.create!(productName:"The Three Bears 199#{i}", longDesc: "Rumor has it that bears accidentally created the greatest wine there is. Experience it now! *Contains no alcohol*",
#              origin: "The Enchanted Forest", grapeType: "Cardinal", bottleSize: 1000, supplier: "DistastefulNonAlcoholicWine co.", price: (20 - i) + 0.95, image_url: "wine_clash.jpg" )
# end

# #supplier2
for i in 1..7 do
  Wine.create!(productName:"Granny Bear 199#{i}", longDesc: "Granny Bear was a mighty and caregiving bear that was known for only fine quality wine *Contains no alcohol*",
               origin: "Narnia", grapeType: "Malingre Precoce", bottleSize: 750, vegetarian: true, supplier: "NobodyLikesWineWithoutAlcohol ltd.", price: (20 - i) + 0.95, image_url: "wine_clash.jpg")
end

Wine.create!(productName:"The Three Bears 1995", longDesc: "Rumor has it that bears accidentally created the greatest wine there is. Experience it now! *Contains no alcohol*",
             origin: "The Enchanted Forest", grapeType: "Cardinal", bottleSize: 1000, supplier: "NobodyLikesWineWithoutAlcohol ltd.", price: 10.95, image_url: "wine_clash.jpg" )

Wine.create!(productName:"The Three Bears 1996", longDesc: "Rumor has it that bears accidentally created the greatest wine there is. Experience it now! *Contains no alcohol*",
             origin: "The Enchanted Forest", grapeType: "Cardinal", bottleSize: 1000, supplier: "NobodyLikesWineWithoutAlcohol ltd.", price: 9.95, image_url: "wine_clash.jpg" )

Wine.create!(productName:"The Three Bears 1997", longDesc: "Rumor has it that bears accidentally created the greatest wine there is. Experience it now! *Contains no alcohol*",
             origin: "The Enchanted Forest", grapeType: "Cardinal", bottleSize: 1000, supplier: "NobodyLikesWineWithoutAlcohol ltd.", price: 8.95, image_url: "wine_clash.jpg" )