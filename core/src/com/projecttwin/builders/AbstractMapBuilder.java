package com.projecttwin.builders;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public abstract class AbstractMapBuilder {

	protected static float ppt = 0;
	protected static String name = "";
	protected static Vector2 position;
	abstract public void dispose();

	public AbstractMapBuilder(float ppt) {
		AbstractMapBuilder.ppt = ppt;
	}

	protected static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
		Rectangle rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		name = rectangleObject.getName();
		Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
				(rectangle.y + rectangle.height * 0.5f) / ppt);
		position = new Vector2(rectangle.x / ppt, rectangle.y / ppt);
		polygon.setAsBox(rectangle.width * 0.5f / ppt, rectangle.height * 0.5f / ppt, size, 0.0f);
		return polygon;
	}
	
	
	
	

	protected static CircleShape getCircle(EllipseMapObject ellipseMapObject) {
		Ellipse ellipse = ellipseMapObject.getEllipse();
		CircleShape circleShape = new CircleShape();
		name = ellipseMapObject.getName();
		circleShape.setRadius(ellipse.width / 2 / ppt);
		circleShape.setPosition(new Vector2(ellipse.x / ppt, ellipse.y / ppt));
		position = new Vector2(ellipse.x / ppt, ellipse.y / ppt);
		return circleShape;
	}

	protected static PolygonShape getPolygon(PolygonMapObject polygonObject) {
		PolygonShape polygon = new PolygonShape();
		name = polygonObject.getName();
		float[] vertices = polygonObject.getPolygon().getTransformedVertices();

		float[] worldVertices = new float[vertices.length];

		for (int i = 0; i < vertices.length; ++i) {
			worldVertices[i] = vertices[i] / ppt;
		}

		polygon.set(worldVertices);
		return polygon;
	}

	protected static ChainShape getPolyline(PolylineMapObject polylineObject) {
		float[] vertices = polylineObject.getPolyline().getTransformedVertices();
		name = polylineObject.getName();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];

		for (int i = 0; i < vertices.length / 2; ++i) {
			worldVertices[i] = new Vector2();
			worldVertices[i].x = vertices[i * 2] / ppt;
			worldVertices[i].y = vertices[i * 2 + 1] / ppt;
		}
		ChainShape chain = new ChainShape();
		chain.createChain(worldVertices);
		return chain;
	}

	protected Shape checkShape(MapObject object) {

		if (object instanceof TextureMapObject) {
			return null;
		}
		Shape shape;

		if (object instanceof RectangleMapObject) {
			shape = getRectangle((RectangleMapObject) object);
		} else if (object instanceof PolygonMapObject) {
			shape = getPolygon((PolygonMapObject) object);
		} else if (object instanceof PolylineMapObject) {
			shape = getPolyline((PolylineMapObject) object);
		} else if (object instanceof EllipseMapObject) {
			shape = getCircle((EllipseMapObject) object);
		} else {
			return null;
		}
		return shape;

	}
}
