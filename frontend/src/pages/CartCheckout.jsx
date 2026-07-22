import { useContext, useState } from 'react';
import { CartContext } from '../context/CartContext';
import { Trash2 } from 'lucide-react';
import api from '../api/axios';

export default function CartCheckout() {
  const { cart, removeFromCart } = useContext(CartContext);
  const [shippingAddress, setShippingAddress] = useState('');
  const [checkingOut, setCheckingOut] = useState(false);

  const handleCheckout = async (e) => {
    e.preventDefault();
    if (!shippingAddress) return alert("Please enter shipping address");
    
    setCheckingOut(true);
    try {
      await api.post('/orders/checkout', { shippingAddress });
      alert("Checkout initialized! Proceeding to PayHere gateway (Mocked).");
      // Redirect or clear
    } catch (e) {
      alert("Checkout failed");
    } finally {
      setCheckingOut(false);
    }
  };

  const total = cart.items?.reduce((sum, item) => sum + (item.productVariant?.price * item.quantity), 0) || 0;

  return (
    <div className="max-w-7xl mx-auto px-4 py-12 w-full grid grid-cols-1 lg:grid-cols-2 gap-12">
      {/* Cart Items */}
      <div className="flex flex-col space-y-6">
        <h1 className="font-display text-4xl font-bold mb-4">Your Cart</h1>
        {!cart.items || cart.items.length === 0 ? (
          <p className="text-text-main/70">Your cart is empty.</p>
        ) : (
          <div className="space-y-4">
            {cart.items.map((item) => (
              <div key={item.id} className="flex items-center gap-4 bg-white p-4 rounded-xl shadow-sm">
                <div className="h-20 w-20 bg-secondary-bg rounded-md overflow-hidden shrink-0">
                  <img src="https://images.unsplash.com/photo-1610030469983-98e550d6193c?auto=format&fit=crop&w=200&q=80" alt="Product" className="w-full h-full object-cover" />
                </div>
                <div className="flex-1">
                  <h3 className="font-medium font-display">{item.productVariant?.product?.name || "Product Name"}</h3>
                  <p className="text-sm text-text-main/70">Qty: {item.quantity}</p>
                </div>
                <div className="text-right flex flex-col items-end gap-2">
                  <p className="font-medium">LKR {item.productVariant?.price * item.quantity}</p>
                  <button onClick={() => removeFromCart(item.id)} className="text-red-500 hover:text-red-700">
                    <Trash2 className="h-5 w-5" />
                  </button>
                </div>
              </div>
            ))}
            <div className="text-right pt-4 border-t border-secondary-bg">
              <p className="text-xl font-bold font-display">Total: LKR {total}</p>
            </div>
          </div>
        )}
      </div>

      {/* Checkout Form */}
      <div className="bg-white p-8 rounded-2xl shadow-sm h-fit border border-secondary-bg">
        <h2 className="font-display text-2xl font-bold mb-6">Checkout</h2>
        <form onSubmit={handleCheckout} className="flex flex-col space-y-4">
          <div>
            <label className="block text-sm font-medium mb-1">Shipping Address</label>
            <textarea 
              rows="3"
              className="w-full px-4 py-2 border border-secondary-bg rounded-md focus:outline-none focus:ring-2 focus:ring-accent-1"
              value={shippingAddress}
              onChange={(e) => setShippingAddress(e.target.value)}
              required
            ></textarea>
          </div>
          <button 
            type="submit" 
            disabled={checkingOut || !cart.items || cart.items.length === 0}
            className="w-full bg-accent-1 text-text-main font-bold py-3 rounded-md hover:bg-accent-1/90 transition-colors disabled:opacity-50 mt-4"
          >
            {checkingOut ? 'Processing...' : 'Proceed to PayHere Gateway'}
          </button>
        </form>
      </div>
    </div>
  );
}
