import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import api from '../api/axios';
import ProductCard from '../components/ProductCard';

export default function ProductList() {
  const { category } = useParams();
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Mock fetch for now, replace with actual API when ready
    // api.get(`/products?category=${category}`)
    setTimeout(() => {
      setProducts([
        { id: 1, name: 'Crimson Silk Saree', variants: [{ id: 101, price: 15000 }] },
        { id: 2, name: 'Midnight Blue Handloom', variants: [{ id: 102, price: 12500 }] },
        { id: 3, name: 'Emerald Party Wear', variants: [{ id: 103, price: 18000 }] },
        { id: 4, name: 'Pastel Lavender Bridal', variants: [{ id: 104, price: 45000 }] },
      ]);
      setLoading(false);
    }, 500);
  }, [category]);

  return (
    <div className="max-w-7xl mx-auto px-4 py-12 w-full">
      <div className="mb-10 text-center">
        <h1 className="font-display text-4xl md:text-5xl font-bold text-text-main capitalize">
          {category ? `${category} Collection` : 'All Products'}
        </h1>
        <p className="mt-4 text-text-main/70 max-w-2xl mx-auto">
          Browse our exclusive range of carefully crafted sarees.
        </p>
      </div>

      {loading ? (
        <div className="flex justify-center py-20"><div className="animate-spin rounded-full h-12 w-12 border-b-2 border-accent-1"></div></div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
          {products.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))}
        </div>
      )}
    </div>
  );
}
