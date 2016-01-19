class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception
  before_action :configure_permitted_parameters, if: :devise_controller?

  def init_suppliers
    @supplier_one = "http://0.0.0.0:3001"
    @supplier_two = "http://0.0.0.0:3002"
  end

  private

  def configure_permitted_parameters
    devise_parameter_sanitizer.for(:sign_up) << :address
    devise_parameter_sanitizer.for(:sign_up) << :name
  end

  def current_basket
    Basket.find(session[:basket_id])
  rescue ActiveRecord::RecordNotFound
    basket = Basket.create
    session[:basket_id] = basket.id
    basket
  end

end
