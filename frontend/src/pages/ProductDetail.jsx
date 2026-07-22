import { useState, useEffect, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { CartContext } from '../context/CartContext';

export default function ProductDetail() {
  const { id } = useParams();
  const { addToCart } = useContext(CartContext);
  const [activeImage, setActiveImage] = useState(0);
  
  // Mock product data
  const product = {
    id: id,
    name: 'Crimson Silk Saree',
    description: 'A beautiful hand-woven crimson silk saree perfect for weddings and special occasions. Features intricate zari work along the border.',
    price: 15000,
    variants: [{ id: 101, size: 'Free', color: 'Crimson' }],
    images: [
      'https://images.unsplash.com/photo-1610030469983-98e550d6193c?auto=format&fit=crop&w=800&q=80',
      'https://images.unsplash.com/photo-1583391733959-f5b9d31190d7?auto=format&fit=crop&w=800&q=80',
      'https://images.unsplash.com/photo-1599839619722-39751411ea63?auto=format&fit=crop&w=800&q=80'
    ]
  };

  const handleAdd = () => {
    addToCart(product.variants[0].id, 1);
  };

  return (
    <div className="max-w-7xl mx-auto px-4 py-12 w-full grid grid-cols-1 md:grid-cols-2 gap-12">
      {/* Left side: Product Info */}
      <div className="flex flex-col space-y-6 order-2 md:order-1">
        <div>
          <h1 className="font-display text-4xl md:text-5xl font-bold text-text-main">{product.name}</h1>
          <p className="text-2xl mt-4 text-text-main/80 font-medium">LKR {product.price}</p>
        </div>
        
        <div className="prose prose-sm text-text-main/80">
          <p>{product.description}</p>
        </div>

        {/* Future Proofing: Measurement Calculator Placeholder */}
        <div className="p-4 border border-accent-2 bg-secondary-bg rounded-lg">
          <h3 className="font-display font-semibold mb-2">Custom Stitching (Coming Soon)</h3>
          <p className="text-sm text-text-main/70">Our measurement calculator will be available here soon for perfect custom fits.</p>
        </div>

        <button 
          onClick={handleAdd}
          className="w-full bg-accent-1 text-text-main font-bold py-4 rounded-lg hover:bg-accent-1/90 transition-colors shadow-sm"
        >
          ADD TO CART
        </button>
      </div>

      {/* Right side: Image Gallery */}
      <div className="flex flex-col md:flex-row gap-4 order-1 md:order-2">
        <div className="flex md:flex-col gap-4 overflow-x-auto md:overflow-visible order-2 md:order-1 w-full md:w-24 shrink-0">
          {product.images.map((img, idx) => (
            <button 
              key={idx} 
              onClick={() => setActiveImage(idx)}
              className={`relative aspect-[3/4] w-20 md:w-full overflow-hidden rounded-md border-2 transition-colors ${activeImage === idx ? 'border-accent-1' : 'border-transparent'}`}
            >
              <img src={img} alt="Thumbnail" className="w-full h-full object-cover" />
            </button>
          ))}
        </div>
        <div className="flex-1 aspect-[3/4] overflow-hidden rounded-xl bg-secondary-bg order-1 md:order-2">
           <img src={product.images[activeImage]} alt="Main product" className="w-full h-full object-cover" />
        </div>
      </div>
    </div>
  );
}
