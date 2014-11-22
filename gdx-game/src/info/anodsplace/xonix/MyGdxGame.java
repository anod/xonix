package info.anodsplace.xonix;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.environment.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g3d.utils.*;

public class MyGdxGame implements ApplicationListener
{
	PerspectiveCamera cam;
	Model field;
	Model xonix;
	ModelBatch modelBatch;
	Array<ModelInstance> instances = new Array<ModelInstance>();
	Environment environment;
	CameraInputController camController;
	
	@Override
	public void create()
	{
		
		modelBatch = new ModelBatch();
		
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(30f, 8f, 0f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

		camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
		
		ModelBuilder modelBuilder = new ModelBuilder();
        field = modelBuilder.createBox(15f, 1f, 15f, 
									   new Material(ColorAttribute.createDiffuse(Color.GREEN)),
									   Usage.Position | Usage.Normal
									   );
									   
		xonix = modelBuilder.createSphere(1f,1f,1f,16,16,
									  new Material(ColorAttribute.createDiffuse(Color.BLUE)),
							  Usage.Position | Usage.Normal
						  );
		
        ModelInstance instance = new ModelInstance(field);
		ModelInstance xonixInst = new ModelInstance(xonix);
		xonixInst.transform.setTranslation(7f,0.95f,7f);
		instances.add(instance);
		instances.add(xonixInst);
		
		environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		
	}

	@Override
	public void render()
	{   
		camController.update();
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
		
	}

	@Override
	public void dispose()
	{
		modelBatch.dispose();
		instances.clear();
		field.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}
}
