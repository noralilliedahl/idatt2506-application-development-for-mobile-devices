package com.app.oving2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.oving2.ui.theme.Oving2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Oving2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RecipeGreeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeGreeting(modifier: Modifier = Modifier) {
    Text(
        text = "Oppskrifter",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun RecipeGreetingPreview() {
    Oving2Theme {
        Screen()
    }
}

@Composable
fun Header(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(MaterialTheme.colorScheme.secondary)
    ) {

        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.image_descriptor),
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = stringResource(R.string.seasonal_badge),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.align(Alignment.TopEnd)
                .padding(12.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        )

        Text(
            text = stringResource(R.string.header),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.BottomStart)
                .padding(16.dp)
        )

    }
}

@Composable
fun RecipeCard(recipe: Recipe, modifier: Modifier = Modifier) {

    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                Toast.makeText(
                    context,
                    recipe.name,
                    Toast.LENGTH_SHORT
                ).show()
            }
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = recipe.category,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = recipe.time,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun RecipeList(recipes: List<Recipe>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(recipes) { recipe ->
            RecipeCard(recipe = recipe)
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun CountButton(recipes: List<Recipe>, modifier: Modifier = Modifier) {
    var context = LocalContext.current
    val toastText = stringResource(R.string.recipe_count_toast, recipes.size)

    Button(
        onClick = {
            Toast.makeText(
                context,
                toastText,
                Toast.LENGTH_SHORT
            ).show()
        },
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.show_count_button))
    }
}

@Composable
fun Screen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Header()

        RecipeList(
            recipes = recipes,
            modifier = Modifier.weight(1f)
        )

        CountButton(
            recipes = recipes,
            modifier = Modifier.padding(16.dp)
        )
    }
}

val recipes = listOf(
    Recipe("Kanelboller", "Bakst", "60 min"),
    Recipe("Pasta Alfredo", "Hovedrett", "30 min"),
    Recipe("Pizza", "Hovedrett", "50 min"),
    Recipe("Tiramisu", "Dessert", "30 min"),
    Recipe("Rundstykker", "Bakst", "45 min"),
    Recipe("Brownies", "Dessert", "35 min")
)