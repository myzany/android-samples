package com.zany.colormatrix;

/**
 * Author : Zany (myzany@mobigen.com / @ohmyzany)
 * Date : 2010-10-15
 * Description : 
 *     - �̹��� ���� ����(Hue/Contrast) �׽�Ʈ
 *     - Pilot Code.
 */
import android.graphics.ColorMatrix;

public class AdjustColorMatrix {

	public static void setTranslate(ColorMatrix cm, float dr, float dg, float db, float da) {
		cm.set(new float[] {
				1, 0, 0, 0, dr,
				0, 1, 0, 0, dg,
				0, 0, 1, 0, db,
				0, 0, 0, 1, da });
	}

	public static void setContrast(ColorMatrix cm, float contrast) {
		float scale = contrast + 1.f;
		float translate = (-.5f * scale + .5f) * 255.f;
		cm.set(new float[] {
				scale, 0, 0, 0, translate,
				0, scale, 0, 0, translate,
				0, 0, scale, 0, translate,
				0, 0, 0, 1, 0 });
	}

	public static void setContrastTranslateOnly(ColorMatrix cm, float contrast) {
		float scale = contrast + 1.f;
		float translate = (-.5f * scale + .5f) * 255.f;
		cm.set(new float[] {
				1, 0, 0, 0, translate,
				0, 1, 0, 0, translate,
				0, 0, 1, 0, translate,
				0, 0, 0, 1, 0 });
	}

	public static void setContrastScaleOnly(ColorMatrix cm, float contrast) {
		float scale = contrast + 1.f;
		cm.set(new float[] {
				scale, 0, 0, 0, 0,
				0, scale, 0, 0, 0,
				0, 0, scale, 0, 0,
				0, 0, 0, 1, 0 });
	}
	
}
