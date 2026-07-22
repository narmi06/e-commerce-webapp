import { Routes, Route, Link, Navigate } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { LayoutDashboard, ShoppingBag, Package } from 'lucide-react';

// Mock components for admin areas
const AdminDashboard = () => <div className="p-8"><h1 className="font-display text-3xl">Dashboard</h1></div>;
const AdminProducts = () => <div className="p-8"><h1 className="font-display text-3xl">Products CRUD Workspace</h1></div>;
const AdminOrders = () => <div className="p-8"><h1 className="font-display text-3xl">Manage Orders</h1></div>;

export default function AdminPortal() {
  const { user, loading } = useContext(AuthContext);

  if (loading) return null;

  // Simple role check based on what we saved in AuthContext
  if (!user || user.role !== 'ADMIN') {
    return <Navigate to="/" />;
  }

  return (
    <div className="flex h-screen w-full bg-primary-bg overflow-hidden absolute inset-0 z-50">
      {/* Sidebar Navigation */}
      <div className="w-64 bg-white border-r border-secondary-bg flex flex-col">
        <div className="h-20 flex items-center justify-center border-b border-secondary-bg">
          <span className="font-display font-extrabold text-2xl">SILKSONG ADMIN</span>
        </div>
        <nav className="flex-1 p-4 space-y-2">
          <Link to="/admin" className="flex items-center gap-3 px-4 py-3 text-text-main hover:bg-accent-1/20 rounded-lg transition-colors">
            <LayoutDashboard className="h-5 w-5" /> Dashboard
          </Link>
          <Link to="/admin/products" className="flex items-center gap-3 px-4 py-3 text-text-main hover:bg-accent-1/20 rounded-lg transition-colors">
            <ShoppingBag className="h-5 w-5" /> Products
          </Link>
          <Link to="/admin/orders" className="flex items-center gap-3 px-4 py-3 text-text-main hover:bg-accent-1/20 rounded-lg transition-colors">
            <Package className="h-5 w-5" /> Orders
          </Link>
        </nav>
        <div className="p-4 border-t border-secondary-bg">
          <Link to="/" className="text-sm text-text-main/70 hover:text-accent-1">Back to Store</Link>
        </div>
      </div>

      {/* Main Workspace */}
      <div className="flex-1 overflow-auto bg-primary-bg p-8">
        <div className="bg-white rounded-2xl shadow-sm min-h-[calc(100vh-4rem)] border border-secondary-bg">
          <Routes>
            <Route path="/" element={<AdminDashboard />} />
            <Route path="/products" element={<AdminProducts />} />
            <Route path="/orders" element={<AdminOrders />} />
          </Routes>
        </div>
      </div>
    </div>
  );
}
