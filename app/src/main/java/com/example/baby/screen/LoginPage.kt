
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baby.R
import com.example.baby.screen.RegisterButton
import com.example.baby.viewModel.LoginViewModel


@Composable
fun LoginPage(viewModel: LoginViewModel, navController: NavController, content: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("로그인", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(30.dp))
        LoginField()
        Spacer(modifier = Modifier.height(20.dp))
        SignInGoogleButton {
            navController.navigate("registerScreen")
//            viewModel.tryLogin(context)
//            if(viewModel.loginResult){
//
//            }
             }
        Spacer(modifier = Modifier.height(20.dp))
//         RegisterButton(isNotNull = true, text= "로그인", route = "mainScreen", navController = navController)
    }
}

@Composable
fun LoginField() {
    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }

    Column() {
        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("ID") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
        OutlinedTextField(
            value = pw,
            onValueChange = { pw = it },
            label = { Text("PASSWORD") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
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
            Icon(painter = painterResource(id = R.drawable.icon_google), contentDescription = "Google sign button", tint = Color.Unspecified, modifier = Modifier.size(35.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Sign in with Google", style = MaterialTheme.typography.overline, color = Color.Gray, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }
    }
}