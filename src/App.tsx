import { useState } from 'react';
import { motion } from 'motion/react';
import { Download, Smartphone, Layers, Activity, Database, FileCode2, ShieldAlert } from 'lucide-react';

export default function App() {
  const [activeTab, setActiveTab] = useState('overview');

  return (
    <div className="min-h-screen bg-[#050810] text-[#E8F4FD] font-sans selection:bg-[#00E5FF]/30">
      {/* Header */}
      <header className="border-b border-white/10 bg-[#0D1B2A]/80 backdrop-blur-md sticky top-0 z-50">
        <div className="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-gradient-to-br from-[#00E5FF] to-[#7B2FBE] flex items-center justify-center shadow-[0_0_15px_rgba(0,229,255,0.4)]">
              <Activity className="w-6 h-6 text-white" />
            </div>
            <div>
              <h1 className="text-xl font-bold tracking-tight">MagnetoMap Pro</h1>
              <p className="text-xs text-[#00E5FF] font-mono tracking-wider uppercase">Android Project Generator</p>
            </div>
          </div>
          <div className="flex items-center gap-4">
            <div className="px-3 py-1 rounded-full bg-emerald-500/10 border border-emerald-500/20 text-emerald-400 text-xs font-mono flex items-center gap-2">
              <span className="w-2 h-2 rounded-full bg-emerald-500 animate-pulse"></span>
              Generating Files...
            </div>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-6 py-12 grid grid-cols-1 lg:grid-cols-12 gap-12">
        {/* Left Column: Info & Instructions */}
        <div className="lg:col-span-5 space-y-8">
          <motion.div 
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            className="space-y-4"
          >
            <h2 className="text-4xl font-bold tracking-tight leading-tight">
              Professional Magnetic <br/>
              <span className="text-transparent bg-clip-text bg-gradient-to-r from-[#00E5FF] to-[#7B2FBE]">
                Survey Instrument
              </span>
            </h2>
            <p className="text-gray-400 text-lg leading-relaxed">
              This environment is generating the complete Android source code for MagnetoMap Pro. 
              Because this is a web-based IDE, the Android APK cannot be compiled here.
            </p>
          </motion.div>

          <div className="bg-[#0D1B2A] border border-white/10 rounded-2xl p-6 space-y-6 shadow-xl">
            <h3 className="text-lg font-semibold flex items-center gap-2">
              <Download className="w-5 h-5 text-[#00E5FF]" />
              How to get your Android App
            </h3>
            
            <ol className="space-y-4 relative before:absolute before:inset-y-0 before:left-[11px] before:w-[2px] before:bg-white/10">
              {[
                "Wait for the AI to finish generating the core Android files in the background.",
                "Click the Settings/Menu icon in the top right of this AI Studio window.",
                "Select 'Export to ZIP' or 'Export to GitHub'.",
                "Open the downloaded project in Android Studio (Hedgehog or newer).",
                "Connect your Android device and click 'Run' to compile the APK."
              ].map((step, i) => (
                <li key={i} className="relative pl-10">
                  <span className="absolute left-0 top-0.5 w-6 h-6 rounded-full bg-[#1a2b42] border border-[#00E5FF]/30 text-[#00E5FF] text-xs flex items-center justify-center font-mono z-10">
                    {i + 1}
                  </span>
                  <p className="text-sm text-gray-300">{step}</p>
                </li>
              ))}
            </ol>
          </div>

          <div className="bg-red-500/10 border border-red-500/20 rounded-2xl p-6">
            <h3 className="text-red-400 font-semibold flex items-center gap-2 mb-2">
              <ShieldAlert className="w-5 h-5" />
              Important Note
            </h3>
            <p className="text-sm text-red-200/80">
              Generating 50+ complex Android files (Kotlin, GLSL, Gradle) exceeds single-response limits. 
              The AI is currently scaffolding the core architecture (Gradle, Manifest, Shaders, Kriging config). 
              You may need to prompt the AI to "continue generating the Kotlin source files" to get the complete project.
            </p>
          </div>
        </div>

        {/* Right Column: Architecture & Status */}
        <div className="lg:col-span-7">
          <div className="bg-[#0D1B2A] border border-white/10 rounded-2xl overflow-hidden shadow-2xl flex flex-col h-full min-h-[600px]">
            <div className="flex border-b border-white/10">
              {[
                { id: 'overview', label: 'Architecture', icon: Layers },
                { id: 'files', label: 'Generated Files', icon: FileCode2 },
                { id: 'tech', label: 'Tech Stack', icon: Database },
              ].map((tab) => (
                <button
                  key={tab.id}
                  onClick={() => setActiveTab(tab.id)}
                  className={`flex-1 py-4 text-sm font-medium flex items-center justify-center gap-2 transition-colors ${
                    activeTab === tab.id 
                      ? 'bg-white/5 text-[#00E5FF] border-b-2 border-[#00E5FF]' 
                      : 'text-gray-400 hover:text-gray-200 hover:bg-white/5'
                  }`}
                >
                  <tab.icon className="w-4 h-4" />
                  {tab.label}
                </button>
              ))}
            </div>

            <div className="p-6 flex-1 overflow-y-auto">
              {activeTab === 'overview' && (
                <div className="space-y-6">
                  <div className="grid grid-cols-2 gap-4">
                    <div className="bg-black/40 p-4 rounded-xl border border-white/5">
                      <h4 className="text-[#00E5FF] font-mono text-xs mb-2">SENSOR LAYER</h4>
                      <p className="text-sm text-gray-400">Madgwick AHRS filter fusing Accelerometer + Gyroscope. Kalman filter for magnetic vector smoothing.</p>
                    </div>
                    <div className="bg-black/40 p-4 rounded-xl border border-white/5">
                      <h4 className="text-[#7B2FBE] font-mono text-xs mb-2">KRIGING ENGINE</h4>
                      <p className="text-sm text-gray-400">Ordinary Kriging with spherical variogram. LU decomposition solver for 3D spatial interpolation.</p>
                    </div>
                    <div className="bg-black/40 p-4 rounded-xl border border-white/5">
                      <h4 className="text-emerald-400 font-mono text-xs mb-2">AR RENDERING</h4>
                      <p className="text-sm text-gray-400">ARCore pose tracking + OpenGL ES 3.1 shaders for volumetric field lines and anomaly isosurfaces.</p>
                    </div>
                    <div className="bg-black/40 p-4 rounded-xl border border-white/5">
                      <h4 className="text-orange-400 font-mono text-xs mb-2">ML CLASSIFIER</h4>
                      <p className="text-sm text-gray-400">On-device TFLite model detecting rebar, pipes, and AC wiring from 12 magnetic features.</p>
                    </div>
                  </div>
                </div>
              )}

              {activeTab === 'files' && (
                <div className="font-mono text-sm text-gray-300 space-y-2">
                  <div className="text-[#00E5FF]">MagnetoMapPro/</div>
                  <div className="pl-4 text-emerald-400 flex items-center gap-2"><span>↳</span> settings.gradle.kts <span className="text-xs bg-emerald-500/20 px-1 rounded text-emerald-300">Generated</span></div>
                  <div className="pl-4 text-emerald-400 flex items-center gap-2"><span>↳</span> build.gradle.kts <span className="text-xs bg-emerald-500/20 px-1 rounded text-emerald-300">Generated</span></div>
                  <div className="pl-4 text-emerald-400 flex items-center gap-2"><span>↳</span> gradle.properties <span className="text-xs bg-emerald-500/20 px-1 rounded text-emerald-300">Generated</span></div>
                  <div className="pl-4">↳ app/</div>
                  <div className="pl-8 text-emerald-400 flex items-center gap-2"><span>↳</span> build.gradle.kts <span className="text-xs bg-emerald-500/20 px-1 rounded text-emerald-300">Generated</span></div>
                  <div className="pl-8">↳ src/main/</div>
                  <div className="pl-12 text-emerald-400 flex items-center gap-2"><span>↳</span> AndroidManifest.xml <span className="text-xs bg-emerald-500/20 px-1 rounded text-emerald-300">Generated</span></div>
                  <div className="pl-12">↳ assets/shaders/</div>
                  <div className="pl-16 text-emerald-400 flex items-center gap-2"><span>↳</span> magnetic_field.frag <span className="text-xs bg-emerald-500/20 px-1 rounded text-emerald-300">Generated</span></div>
                  <div className="pl-16 text-emerald-400 flex items-center gap-2"><span>↳</span> isosurface.frag <span className="text-xs bg-emerald-500/20 px-1 rounded text-emerald-300">Generated</span></div>
                  <div className="pl-12">↳ assets/config/</div>
                  <div className="pl-16 text-emerald-400 flex items-center gap-2"><span>↳</span> kriging_params.json <span className="text-xs bg-emerald-500/20 px-1 rounded text-emerald-300">Generated</span></div>
                  <div className="pl-12 text-gray-500">↳ kotlin/com/magnetomap/pro/... (Pending)</div>
                  <div className="pl-12 text-gray-500">↳ res/... (Pending)</div>
                </div>
              )}

              {activeTab === 'tech' && (
                <ul className="space-y-3">
                  {[
                    "Kotlin 1.9.22 (100%)",
                    "Gradle 8.2.0 (Kotlin DSL)",
                    "ARCore 1.43.0 & Sceneform 1.23.0",
                    "TensorFlow Lite 2.14.0 (GPU Delegate)",
                    "Room 2.6.1 & Hilt 2.50 (KSP)",
                    "OpenGL ES 3.1",
                    "MPAndroidChart 3.1.0",
                    "iText7 Core 7.2.5"
                  ].map((tech, i) => (
                    <li key={i} className="flex items-center gap-3 text-gray-300">
                      <div className="w-1.5 h-1.5 rounded-full bg-[#7B2FBE]"></div>
                      {tech}
                    </li>
                  ))}
                </ul>
              )}
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
