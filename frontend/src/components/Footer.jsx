import { Link } from 'react-router-dom';

export default function Footer() {
  return (
    <footer className="bg-accent-2 mt-auto">
      <div className="max-w-7xl mx-auto py-12 px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 items-center">
          <div>
            <h2 className="font-display text-2xl font-bold mb-2 text-text-main">Subscribe to Newsletter</h2>
            <p className="text-sm text-text-main/80 mb-4">Get the latest updates on new collections and exclusive offers.</p>
            <form className="flex space-x-2">
              <input
                type="email"
                placeholder="Enter your email"
                className="flex-1 px-4 py-2 border border-secondary-bg rounded-md focus:outline-none focus:ring-2 focus:ring-accent-1"
                required
              />
              <button
                type="submit"
                className="bg-accent-1 hover:bg-accent-1/80 text-text-main font-medium px-6 py-2 rounded-md transition-colors"
              >
                Subscribe
              </button>
            </form>
          </div>
          <div className="flex flex-col items-start md:items-end space-y-4">
             <Link to="/" className="font-display font-extrabold text-2xl tracking-tighter">SILKSONG</Link>
             <p className="text-sm text-text-main/80">© {new Date().getFullYear()} SILKSONG. All rights reserved.</p>
          </div>
        </div>
      </div>
    </footer>
  );
}
