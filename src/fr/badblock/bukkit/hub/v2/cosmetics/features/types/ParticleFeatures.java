package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.AnimatedBallParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ArcParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.AtomParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.BleedParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.CircleParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.CloudParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ConeParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.CubeParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.CustomParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.CylinderParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.DiscoBallParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.DnaParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.DonutParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.DragonParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.EarthParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.EquationParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.FlameParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.FountainParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.GridParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.HeartParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.HelixParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.HillParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.JumpParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.LineParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.LoveParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.MusicParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ShieldParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.SkyRocketParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.SmokeParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.SphereParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.StarParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.TornadoParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.TraceParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.TurnParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.VortexParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.WarpParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.WaveParticle;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

@Getter
public enum ParticleFeatures implements IFeatureWorker
{

	ANIMATEDBALL(new AnimatedBallParticle()),
	ARC(new ArcParticle()),
	ATOM(new AtomParticle()),
	BLEED(new BleedParticle()),
	CIRCLE(new CircleParticle()),
	CLOUD(new CloudParticle()),
	CONE(new ConeParticle()),
	CUBE(new CubeParticle()),
	CYLINDER(new CylinderParticle()),
	DISCOBALL(new DiscoBallParticle()),
	DNA(new DnaParticle()),
	DONUT(new DonutParticle()),
	DRAGON(new DragonParticle()),
	EARTH(new EarthParticle()),
	EQUATION(new EquationParticle()),
	FLAME(new FlameParticle()),
	FOUNTAIN(new FountainParticle()),
	GRID(new GridParticle()),
	HEART(new HeartParticle()),
	HELIX(new HelixParticle()),
	HILL(new HillParticle()),
	JUMP(new JumpParticle()),
	LINE(new LineParticle()),
	LOVE(new LoveParticle()),
	MUSIC(new MusicParticle()),
	SHIELD(new ShieldParticle()),
	SKYROCKET(new SkyRocketParticle()),
	SMOKE(new SmokeParticle()),
	SPHERE(new SphereParticle()),
	STAR(new StarParticle()),
	TORNADO(new TornadoParticle()),
	TRACE(new TraceParticle()),
	TURN(new TurnParticle()),
	VORTEX(new VortexParticle()),
	WARP(new WarpParticle()),
	WAVE(new WaveParticle());

	private CustomParticle customParticle;

	ParticleFeatures(CustomParticle customParticle)
	{
		setCustomParticle(customParticle);
	}

	public void setCustomParticle(CustomParticle customParticle)
	{
		this.customParticle = customParticle;
	}

	@Override
	public void work(BadblockPlayer player)
	{
		HubPlayer hubPlayer = HubPlayer.get(player);
		
		if (hubPlayer == null)
		{
			return;
		}
		
		if (hubPlayer.getEffect() != null)
		{
			hubPlayer.getEffect().cancel();
			hubPlayer.setEffect(null);
		}
		
		getCustomParticle().start(player);
	}

	public static void work(BadblockPlayer player, String featureName)
	{
        String[] parser = featureName.split("_");

        if (parser.length < 2) {
            return;
        }

        String name = parser[1];

        Feature feature = ConfigLoader.getFeatures().getFeatures().get(featureName);

        if (feature == null)
        {
        	return;
        }
        
        ParticleFeatures finalFeature = null;
        for (ParticleFeatures particleFeature : values()) {
            if (particleFeature.name().equalsIgnoreCase(name)) {
                finalFeature = particleFeature;
                break;
            }
        }
        
        if (finalFeature == null) {
            return;
        }
        
		finalFeature.work(player);
	}

}
