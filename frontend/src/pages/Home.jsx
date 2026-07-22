import { Link } from 'react-router-dom';

export default function Home() {
  return (
    <div className="flex flex-col">
      {/* Hero Section */}
      <section className="relative h-[80vh] w-full bg-secondary-bg flex items-center justify-center overflow-hidden">
        {/* <!-- TODO: Replace with actual SILKSONG product image --> */}
        <div className="absolute inset-0">
           <img 
             src="https://images.unsplash.com/photo-1583391733959-f5b9d31190d7?auto=format&fit=crop&w=1920&q=80" 
             alt="Hero Saree" 
             className="w-full h-full object-cover opacity-60"
           />
        </div>
        <div className="relative z-10 text-center px-4">
          <h1 className="font-display font-extrabold text-5xl md:text-7xl tracking-tighter text-text-main mb-6 drop-shadow-sm">
            ELEVATE YOUR <br className="hidden md:block"/> ELEGANCE
          </h1>
          <p className="mt-4 max-w-2xl text-xl text-text-main/90 mx-auto mb-8 font-medium">
            Discover the new bridal and party collections by SILKSONG.
          </p>
          <Link to="/collection/party" className="inline-block bg-accent-1 text-text-main font-bold py-4 px-10 rounded-full hover:bg-accent-1/90 transition-transform hover:scale-105 shadow-md">
            SHOP THE COLLECTION
          </Link>
        </div>
      </section>

      {/* Categories / Editorial Blocks */}
      <section className="max-w-7xl mx-auto px-4 py-20 w-full grid grid-cols-1 md:grid-cols-2 gap-8">
        
        <Link to="/collection/work" className="group relative h-[500px] overflow-hidden rounded-2xl">
          {/* <!-- TODO: Replace with actual SILKSONG product image --> */}
          <img src="https://images.unsplash.com/photo-1610030469983-98e550d6193c?auto=format&fit=crop&w=800&q=80" alt="Work Collection" className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110" />
          <div className="absolute inset-0 bg-gradient-to-t from-primary-bg/90 to-transparent flex flex-col justify-end p-8">
            <h2 className="font-display text-4xl font-bold text-text-main mb-2">Work Collection</h2>
            <span className="text-accent-1 font-medium group-hover:underline">Explore More &rarr;</span>
          </div>
        </Link>

        <div className="flex flex-col gap-8 h-[500px]">
          <Link to="/collection/party" className="group relative flex-1 overflow-hidden rounded-2xl">
            <img src="https://images.unsplash.com/photo-1599839619722-39751411ea63?auto=format&fit=crop&w=800&q=80" alt="Party Collection" className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110" />
            <div className="absolute inset-0 bg-primary-bg/20 group-hover:bg-primary-bg/10 transition-colors flex items-center justify-center">
               <h2 className="font-display text-3xl font-bold text-primary-bg drop-shadow-md bg-text-main/40 px-6 py-2 rounded">Party Wear</h2>
            </div>
          </Link>
          <Link to="/collection/bridal" className="group relative flex-1 overflow-hidden rounded-2xl bg-accent-2 flex items-center justify-center">
             <div className="text-center p-6">
                <h2 className="font-display text-3xl font-bold text-text-main mb-2">Bridal <br/>Exclusive</h2>
                <p className="text-sm text-text-main/70">Custom stitching available</p>
             </div>
          </Link>
        </div>

      </section>
    </div>
  );
}
