package com.revsb_11.views.components

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.api.services.drive.DriveScopes
import com.revsb_11.viewmodels.SelectedFilesViewModel

@Composable
fun GoogleSignInButton(viewModel: SelectedFilesViewModel?, context: Context, modifier: Modifier) {
    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel?.setGoogleAccount(account)
                // Теперь вы можете вызвать getFolders()
            } catch (e: ApiException) {
                // Обработка ошибки входа в систему
            }
        }
    }

    Button(
        modifier = modifier,
        onClick = {
            val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(Scope(DriveScopes.DRIVE))
                .build()

            val signInClient = GoogleSignIn.getClient(context, signInOptions)
            val signInIntent = signInClient.signInIntent
            signInLauncher.launch(signInIntent)
        }) {
        Text(
            text = "Войти в Google",
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}