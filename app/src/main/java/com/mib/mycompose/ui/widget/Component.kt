package com.mib.mycompose.ui.widget

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.annotation.RawRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mib.mycompose.R
import com.mib.mycompose.ext.toast
import com.mib.mycompose.ui.main.CaseData
import com.mib.mycompose.ui.theme.C_111111
import com.mib.mycompose.ui.theme.C_Black_50
import com.mib.mycompose.ui.theme.C_Main
import com.mib.mycompose.ui.theme.editTextHintStyle
import com.mib.mycompose.viewmodel.NavNavControllerViewModel

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
    Surface(
        color = Color.LightGray,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(16.dp)
    ) {
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
@Preview
@Composable
fun BasicTextFieldWithHint(
    modifier: Modifier = Modifier,
    hint: String = "asdasdasdasdasda",
    onValueChange: (value : String) -> Unit = {},
    isPassword: Boolean = false,
    defaultText: String = "",
) {
    var text by remember { mutableStateOf(defaultText) }
    var isShowIcon by remember { mutableStateOf(isPassword) }
    var isShowEditContext by remember { mutableStateOf(isShowIcon) }
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Green)
    ) {
        val (editText, tvHint) = createRefs()

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
                    height = Dimension.wrapContent
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
                    color = colorResource(id = R.color.white),
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
                        end.linkTo(editText.end)
                        width = Dimension.fillToConstraints // 横向占满
                        height = Dimension.wrapContent
                    }
                    .wrapContentHeight(),
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Preview
@Composable
fun BasicTextFieldWithPassword(
    modifier: Modifier = Modifier,
    hint: String = "testasdasdasdasdasdASD",
    onValueChange: (value: String) -> Unit = {},
    isPassword: Boolean = false
) {
    var text by remember { mutableStateOf("") }
    var isShowIcon by remember { mutableStateOf(isPassword) }
    var isShowEditContext by remember { mutableStateOf(isShowIcon) }
    ConstraintLayout(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(colorResource(id = R.color.purple_200)),
    ) {
        val (editText, tvHint, ivPwdIcon) = createRefs()

        Image(
            painter = painterResource(id = if (isShowEditContext) R.drawable.icon_eye_close else R.drawable.icon_eye_open),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(ivPwdIcon) {
                    top.linkTo(editText.top)
                    bottom.linkTo(editText.bottom)
                    end.linkTo(parent.end)
                }
                .padding(vertical = 5.dp, horizontal = 4.dp)
                .background(color = colorResource(id = R.color.white))
                .height(20.dp)
                .width(20.dp)
                .clickable {
                    isShowEditContext = !isShowEditContext
                }
            ,
            contentScale = ContentScale.Inside
        )

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
                    end.linkTo(ivPwdIcon.start, margin = 10.dp)
                    width = Dimension.preferredWrapContent
                }
                .wrapContentHeight()
                .background(color = Color.Red)
                .padding(vertical = 10.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Number
            ),
            visualTransformation = if (isShowEditContext) PasswordVisualTransformation() else VisualTransformation.None,
            decorationBox = { innerTextField ->
                Surface(
                    color = colorResource(id = R.color.white),
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    innerTextField()
                }
            },
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
                        end.linkTo(ivPwdIcon.start, margin = 5.dp)
                        width = Dimension.fillToConstraints
                    }
                    .background(color = colorResource(id = R.color.transparent))
                    .wrapContentHeight(),
                maxLines = 1,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Composable
fun TextWithEndIcon(
    text: String,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    res: Int,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = text, fontSize = fontSize, fontWeight = fontWeight, modifier = textModifier)
        Image(
            painter = painterResource(id = res),
            contentDescription = null,
            modifier = Modifier.padding(start = 4.dp),

            )
    }
}

@Composable
fun TextWithStartIcon(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.Normal,
    res: Int,
    color: Color,
    textModifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = res),
            contentDescription = null,
            modifier = Modifier.padding(end = 5.dp),

            )
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            modifier = textModifier,
            color = color
        )
    }
}

@Composable
fun CaseDataTabContent(
    modifier: Modifier = Modifier,
    caseDataList: List<CaseData>?,
    tabClickListener: (index: Int) -> Unit = {}
) {
    val selectIndexState = remember { mutableStateOf(0) }
    val titles = listOf<String>("Unpaid cases", "Paid cases")
    Column {
        TabRow(selectedTabIndex = selectIndexState.value) {
            titles.forEachIndexed { index, value ->
                Tab(
                    selected = selectIndexState.value == index,
                    onClick = {
                        selectIndexState.value = index
                    }
                ) {

                }
            }
        }
    }

}

@Composable
fun MainBackHandler() {
    val context = LocalContext.current
    BackHandler(enabled = true) {
        context.toast("当前为主页，不能返回")
    }
}


@Composable
fun CircularProgressIndicator(isShow: Boolean = false) {
    if (isShow) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = C_Black_50),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            androidx.compose.material.CircularProgressIndicator(
                modifier = Modifier.padding(16.dp),
                color = C_Main
            )

        }
    }
}

@Composable
fun WrapperConstraintLayout(modifier: Modifier, content: @Composable () -> Unit) {
    ConstraintLayout(
        modifier = modifier
    ) {
        content()
    }
}

@Preview
@Composable
fun BackTitleLayout(
    modifier: Modifier = Modifier,
    title: String? = "asdasd",
    content: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BackTile(title = title)
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Preview
@Composable
fun BackTile(
    modifier: Modifier = Modifier,
    title: String? = "title",
    rightRes: Int = 0
) {
    val navViewModel: NavNavControllerViewModel =
        viewModel(LocalContext.current as ComponentActivity)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentHeight()
                .wrapContentWidth()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null // 无波纹效果
                ) {
                    navViewModel.navController.popBackStack()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.mipmap.icon_back),
                contentDescription = null,
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(8.dp),
                contentScale = ContentScale.None
            )
        }
        // 添加一个 Spacer 占用剩余空间
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = title ?: "",
            color = C_111111,
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .background(Color.White)
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(16.dp)
        )
        // 添加一个 Spacer 占用剩余空间
        Spacer(modifier = Modifier.weight(1f))
        if (rightRes != 0) {
            Image(
                painter = painterResource(id = rightRes),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight()
                    .wrapContentWidth(),
                contentScale = ContentScale.Inside
            )
        } else {
            Spacer(
                modifier = Modifier
                    .padding(16.dp)
                    .height(20.dp)
                    .width(20.dp),
            )
        }

    }
}

@Preview
@Composable
fun CommonInputText(
    modifier: Modifier = Modifier,
    hint: String = "",
    onValueChange: (value: String) -> Unit = {},
    isPassword: Boolean = false,
    defaultText: String = "",
) {
    var value by remember { mutableStateOf(defaultText) }
    val isPwdState by remember { mutableStateOf(isPassword) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = { content ->
                value = content
                onValueChange(content)
            },
            textStyle = editTextHintStyle,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(BorderStroke(0.dp, Color.Transparent)),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Number
            ),
            placeholder = {
                Text(text = hint)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent, // 未聚焦时边框颜色设置为透明
                focusedBorderColor = Color.Transparent,   // 聚焦时边框颜色设置为透明
                disabledBorderColor = Color.Transparent,  // 禁用状态下边框颜色设置为透明
                errorBorderColor = Color.Transparent      // 错误状态下边框颜色设置为透明
            ),

            )
    }
}

