import android.content.Context
import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baby.R
import com.example.baby.ui.theme.StartFontStyle
import com.example.baby.ui.theme.nanumSquare


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(content: () -> Unit, context: Context) {
    var appName = context.resources.getString(R.string.app_name)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "${appName}",
                        style = StartFontStyle.startButton,
                        color = colorResource(id = R.color.secondary_color),
                        //modifier = Modifier.align(Alignment.CenterVertically)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color(R.color.secondary_color)
                        )
                    }
                },
                //colors = Color(R.color.white)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 40.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text("환영합니다!", style = StartFontStyle.startSubtitle)
            Spacer(modifier = Modifier.height(9.dp))
            Text("${appName}과 함께 하세요.", style = StartFontStyle.startHeadline)
            Spacer(modifier = Modifier.height(20.dp))
            SignInGoogleButton {
                content()
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
                Text(
                    "아직 회원아 아니신가요?",
                    style = StartFontStyle.startBody1,
                    color = Color(R.color.gray4),
                    fontWeight = FontWeight.Light
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "",
                    tint = colorResource(
                        id = R.color.gray4
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = "login",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun SignInGoogleButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        // border = BorderStroke(width = 1.dp, color = Color.LightGray),
        color = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.small,
        elevation = 10.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(
                start = 14.dp,
                end = 12.dp,
                top = 11.dp,
                bottom = 11.dp
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_google),
                contentDescription = "Google sign button",
                tint = Color.Unspecified,
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Sign in with Google",
                style = MaterialTheme.typography.overline,
                color = Color.Gray,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}