import { Link } from 'react-router-dom';
import { ShoppingCart } from 'lucide-react';
import { useContext } from 'react';
import { CartContext } from '../context/CartContext';

export default function ProductCard({ product }) {
  const { addToCart } = useContext(CartContext);

  // Assuming product has variants, taking the first one for price and default add
  const defaultVariant = product.variants?.[0];
  const price = defaultVariant?.price || 0;

  const handleAdd = (e) => {
    e.preventDefault(); // Prevent navigating to product detail
    if (defaultVariant) {
      addToCart(defaultVariant.id, 1);
    }
  };

  return (
    <Link to={`/product/${product.id}`} className="group relative block overflow-hidden rounded-xl bg-white shadow-sm hover:shadow-md transition-shadow">
      <div className="aspect-[3/4] w-full overflow-hidden bg-secondary-bg">
        {/* <!-- TODO: Replace with actual SILKSONG product image --> */}
        <img
          src={`https://images.unsplash.com/photo-1610030469983-98e550d6193c?auto=format&fit=crop&w=500&q=80`}
          alt={product.name}
          className="h-full w-full object-cover object-center transition-transform duration-500 group-hover:scale-105"
        />
      </div>
      <div className="p-4 bg-primary-bg flex justify-between items-start">
        <div>
          <h3 className="text-sm font-medium text-text-main font-display">{product.name}</h3>
          <p className="mt-1 text-sm text-text-main/70">LKR {price}</p>
        </div>
        <button 
          onClick={handleAdd}
          className="bg-accent-1 text-text-main p-2 rounded-full hover:bg-accent-1/80 transition-colors shadow-sm"
          title="Add to Cart"
        >
          <ShoppingCart className="h-4 w-4" />
        </button>
      </div>
    </Link>
  );
}
