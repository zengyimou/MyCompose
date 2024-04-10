package com.mib.mycompose.ui.widget

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mib.mycompose.R
import com.mib.mycompose.ui.theme.editTextHintStyle

/**
 *  author : cengyimou
 *  date : 2023/11/30 15:51
 *  description :
 */
@Composable
fun SimpleText(text: String) {
	Text(
		text = text
	)
}

@Composable
fun StyleText(displayText: String, style: TextStyle? = null, maxLines: Int? = null) {
	Text(
		text = displayText,
		modifier = Modifier.absolutePadding(16.dp, 10.dp, 16.dp, 10.dp),
		style = style ?: TextStyle.Default,
		overflow = TextOverflow.Ellipsis,
		maxLines = maxLines ?: Int.MAX_VALUE
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTextFieldComponent() {
	Surface(color = Color.LightGray, shape = RoundedCornerShape(10.dp), modifier = Modifier.padding(16.dp)) {
		var text by remember { mutableStateOf(TextFieldValue("Enter text here")) }
		TextField(
			value = text,
			modifier = Modifier
				.padding(16.dp)
				.fillMaxWidth(),
			onValueChange = {
				Log.d("", "it:$it")
				text = it
			}
		)
	}
}

/**
 * 自定义输入框
 * @param hint String
 * @param onValueChange Function1<[@kotlin.ParameterName] TextFieldValue, Unit>
 * @param modifier Modifier?
 * @param isPassword Boolean
 */
@Composable
fun BasicTextFieldWithHint(
	hint: String,
	onValueChange: (value: String) -> Unit,
	modifier: Modifier? = Modifier
		.fillMaxWidth()
		.padding(16.dp),
	isPassword: Boolean = false
) {
	var text by remember { mutableStateOf("") }
	var isShowIcon by remember { mutableStateOf(isPassword) }
	var isShowEditContext by remember { mutableStateOf(isShowIcon) }
	Box(
		modifier = modifier ?: Modifier
	) {
		ConstraintLayout(
			modifier = Modifier
				.fillMaxWidth(1f)
				.wrapContentHeight()
		) {
			val (editText, tvHint, ivPwdIcon) = createRefs()

			BasicTextField(
				value = text,
				onValueChange = {
					text = it
					onValueChange(it)
				},
				textStyle = editTextHintStyle,
				modifier = Modifier
					.constrainAs(editText) {
						top.linkTo(parent.top)
						bottom.linkTo(parent.bottom)
						start.linkTo(parent.start)
						end.linkTo(ivPwdIcon.start)
						width = Dimension.fillToConstraints // 横向占满
					}
//					.fillMaxWidth()
					.wrapContentHeight()
					.padding(vertical = 10.dp),
				singleLine = true,
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Done,
					keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Number
				),
				visualTransformation = if (isShowEditContext) PasswordVisualTransformation() else VisualTransformation.None,
				decorationBox = { innerTextField ->
					Surface(
						color = colorResource(id = R.color.purple_500),
						modifier = Modifier
							.wrapContentHeight()
							.fillMaxWidth()
					) {
						innerTextField()
					}
				}
			)
			if (text.isEmpty()) {
				Text(
					text = hint,
					style = editTextHintStyle,
					modifier = Modifier
						.constrainAs(tvHint) {
							top.linkTo(editText.top)
							bottom.linkTo(editText.bottom)
							start.linkTo(editText.start)
							end.linkTo(ivPwdIcon.start)
							width = Dimension.fillToConstraints // 横向占满
						}
						.wrapContentHeight(),
					textAlign = TextAlign.Start,
				)
			}
		}
	}
}

@Composable
fun BasicTextFieldWithPassword(
	hint: String,
	onValueChange: (value: String) -> Unit,
	modifier: Modifier? = Modifier
		.fillMaxWidth()
		.padding(16.dp),
	isPassword: Boolean = false
) {
	var text by remember { mutableStateOf("") }
	var isShowIcon by remember { mutableStateOf(isPassword) }
	var isShowEditContext by remember { mutableStateOf(isShowIcon) }
	Box(
		modifier = modifier ?: Modifier
	) {
		ConstraintLayout(
			modifier = Modifier
				.fillMaxWidth(1f)
				.wrapContentHeight()
				.background(colorResource(id = R.color.colorPrimary)),
		) {
			val (editText, tvHint, ivPwdIcon) = createRefs()

			BasicTextField(
				value = text,
				onValueChange = {
					text = it
					onValueChange(it)
				},
				textStyle = editTextHintStyle,
				modifier = Modifier
					.constrainAs(editText) {
						top.linkTo(parent.top)
						bottom.linkTo(parent.bottom)
						start.linkTo(parent.start)
						end.linkTo(ivPwdIcon.start)
						width = Dimension.fillToConstraints // 横向占满
					}
//					.fillMaxWidth()
					.wrapContentHeight()
					.padding(vertical = 10.dp),
				singleLine = true,
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Done,
					keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Number
				),
				visualTransformation = if (isShowEditContext) PasswordVisualTransformation() else VisualTransformation.None,
				decorationBox = { innerTextField ->
					Surface(
						color = colorResource(id = R.color.purple_500),
						modifier = Modifier
							.wrapContentHeight()
							.fillMaxWidth()
					) {
						innerTextField()
					}
				}
			)
			if (text.isEmpty()) {
				Text(
					text = hint,
					style = editTextHintStyle,
					modifier = Modifier
						.constrainAs(tvHint) {
							top.linkTo(editText.top)
							bottom.linkTo(editText.bottom)
							start.linkTo(editText.start)
							end.linkTo(ivPwdIcon.start)
							width = Dimension.fillToConstraints // 横向占满
						}
						.background(color = colorResource(id = R.color.colorAccent))
						.wrapContentHeight(),
					textAlign = TextAlign.Start,
				)
			}
			if(isShowIcon){
				Image(
					painter = painterResource(id = if (isShowEditContext) R.drawable.icon_eye_close else R.drawable.icon_eye_open),
					contentDescription = "",
					modifier = Modifier
						.constrainAs(ivPwdIcon) {
							top.linkTo(editText.top)
							bottom.linkTo(editText.bottom)
							start.linkTo(editText.end)
							end.linkTo(parent.end)
						}
						.padding(vertical = 5.dp)
						.background(color = colorResource(id = R.color.purple_500))
						.wrapContentHeight()
						.wrapContentWidth()
						.alpha(if (isShowIcon) 1f else 0f),

					)
			}

		}
	}
}