package com.mib.mycompose.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mib.mycompose.ui.theme.C_30B284
import com.mib.mycompose.ui.theme.C_F8F8F8

/**
 *  author : cengyimou
 *  date : 2024/4/12 16:03
 *  description :
 */
@Preview
@Composable
fun CircleRing(modifier: Modifier = Modifier, radius: Float = 60F,  pointOfYearPercent: Float = 40F, content: String = "测试", label: String = "label xxxxxx"){
	Canvas(modifier = modifier.size((radius * 3).dp, (radius * 2).dp), onDraw = {
		val strokeWidth = 30F
		val topLeft = Offset(radius - strokeWidth, radius - strokeWidth)
//		val topLeft = Offset.Zero
		val size = Size(radius * 7, radius * 7)
		//灰色背景
		drawArc(
			C_F8F8F8,
			startAngle = 180f,
			sweepAngle = 180f,
			useCenter = false,
			topLeft = topLeft,
			size = size,
			style = Stroke(strokeWidth, cap = StrokeCap.Round),
		)

		drawArc(
			C_30B284,
			startAngle = 180f,
			sweepAngle = pointOfYearPercent,
			useCenter = false,
			topLeft = topLeft,
			size = size,
			style = Stroke(strokeWidth, cap = StrokeCap.Round),
		)

		drawIntoCanvas { canvas ->
			val paint = androidx.compose.ui.graphics.Paint().asFrameworkPaint()
			paint.textSize = 40f
			paint.color = android.graphics.Color.BLACK
			paint.textAlign = android.graphics.Paint.Align.LEFT
			paint.isFakeBoldText = true
			val x = size.width / 2 - paint.measureText(content) / 2
			val y = size.height / 2 + paint.fontMetrics.descent
			canvas.nativeCanvas.drawText(content, x, y, paint)
		}

		drawIntoCanvas { canvas ->
			val paint = androidx.compose.ui.graphics.Paint().asFrameworkPaint()
			paint.textSize = 20f
			paint.color = android.graphics.Color.BLACK
			paint.textAlign = android.graphics.Paint.Align.LEFT
			val x = size.width / 2 - paint.measureText(label) / 2
			val y = size.height / 2
			canvas.nativeCanvas.drawText(label, x, y, paint)
		}

	})

}