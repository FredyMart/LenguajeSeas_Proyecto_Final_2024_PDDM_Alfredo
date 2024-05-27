package com.example.lenguajeseas

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lenguajeseas.data.Datasource
import com.example.lenguajeseas.model.Comida
import com.example.lenguajeseas.model.Mes
import com.example.lenguajeseas.model.Semana
import com.example.lenguajeseas.ui.theme.LenguajeSeasTheme
import com.example.lenguajeseas.viewmodel.FavoritesViewModel
import com.example.lenguajeseas.viewmodel.SearchViewModel

// MainActivity es la actividad principal de la aplicación
/**
 * Clase principal de la actividad de la aplicación.
 */
class MainActivity : ComponentActivity() {

    // ViewModel para la gestión de favoritos
    private val favoritesViewModel: FavoritesViewModel by viewModels()

    /**
     * Método onCreate de la actividad.
     *
     * @param savedInstanceState El estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fija la orientación de la pantalla a vertical
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Establece el contenido de la actividad con el tema y la navegación de la aplicación
        setContent {
            LenguajeSeasTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Composable que define la navegación de la aplicación
                    AppNavigation(favoritesViewModel)
                }
            }
        }
    }
}

// AppNavigation contiene la estructura de navegación de la aplicación
/**
 * Composable que define la navegación principal de la aplicación.
 *
 * @param favoritesViewModel El ViewModel para manejar los favoritos en la aplicación.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(favoritesViewModel: FavoritesViewModel) {
    // Controlador de navegación
    val navController = rememberNavController()

    // Scaffold con AppBar y BottomNavigationBar
    Scaffold(
        // Configuración de la AppBar
        topBar = {
            // AppBar personalizada
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            if (currentDestination?.route != "Home") {
                // AppBar con botón de navegación hacia atrás
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.lenguaje_de_se_as),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        // Botón de navegación hacia atrás
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            }
                        ) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            } else {
                // AppBar estándar
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.lenguaje_de_se_as),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        },
        // Configuración del BottomNavigationBar
        bottomBar = {
            // BottomNavigationBar
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        // Contenido del NavHost
        NavHost(navController = navController, startDestination = "Home", modifier = Modifier.padding(paddingValues)) {
            // Destinos de las pantallas
            composable("Home") { SelasApp(navController = navController) }
            composable("Abc") { AbcScreen() }
            composable("Categories") { CategoriesScreen(navController = navController) }
            composable("Comida") { ComidaScreen(favoritesViewModel) }
            composable("Semana") { SemanaScreen(favoritesViewModel) }
            composable("Mes") { MesScreen(favoritesViewModel) }
            composable("Favorites") { FavoritesScreen(favoritesViewModel) }
            composable("Search") { SearchScreen(viewModel = viewModel()) }
        }
    }
}


// SelasApp es la pantalla principal de la aplicación
/**
 * Composable que representa la pantalla principal de la aplicación.
 *
 * @param navController El controlador de navegación para manejar la navegación entre pantallas.
 */
@Composable
fun SelasApp(navController: NavHostController) {
    // Contenido de la pantalla principal
    Scaffold(
        // El contenido del Scaffold, que incluye el AppBar y el cuerpo de la pantalla.
        content = { paddingValues ->
            // Surface que contiene todo el contenido de la pantalla.
            Surface(modifier = Modifier.fillMaxSize(), color = Color.Unspecified) {
                Column(
                    // Columna que contiene todos los elementos de la pantalla, con relleno en los bordes.
                    modifier = Modifier
                        .fillMaxSize() // Ocupa todo el tamaño disponible.
                        .padding(paddingValues) // Agrega un relleno basado en las reglas de Material Design.
                        .padding(horizontal = 16.dp, vertical = 40.dp), // Relleno adicional en los bordes.
                    verticalArrangement = Arrangement.Center, // Alineación vertical al centro.
                    horizontalAlignment = Alignment.CenterHorizontally // Alineación horizontal al centro.
                ) {
                    // Texto de bienvenida en la parte superior de la pantalla.
                    Text(
                        text = stringResource(R.string.Bienvenida), // Texto obtenido de los recursos.
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ), // Estilo del texto (negrita).
                        modifier = Modifier
                            .fillMaxWidth() // Ocupa todo el ancho disponible.
                            .padding(16.dp), // Añade un relleno alrededor del texto.
                        textAlign = TextAlign.Center // Alineación del texto al centro.
                    )

                    // Botón de navegación para el abecedario de señas.
                    ButtonWithIconAndText(
                        iconResId = R.drawable.bloques, // ID del recurso del ícono.
                        buttonText = stringResource(id = R.string.abecedario_de_se_as), // Texto del botón obtenido de los recursos.
                        onClick = { navController.navigate("Abc") } // Acción al hacer clic en el botón (navega a la pantalla de abecedario).
                    )

                    // Espacio entre los botones.
                    Spacer(modifier = Modifier.height(40.dp))

                    // Botón de navegación para aprender el lenguaje de señas.
                    ButtonWithIconAndText(
                        iconResId = R.drawable.lenguaje_de_senas, // ID del recurso del ícono.
                        buttonText = stringResource(id = R.string.aprende_lenguaje_de_se_as), // Texto del botón obtenido de los recursos.
                        onClick = { navController.navigate("Categories") } // Acción al hacer clic en el botón (navega a la pantalla de categorías).
                    )
                }
            }
        }
    )
}

// Función que muestra un botón con icono y texto
/**
 * Composable que muestra un botón con un ícono y texto debajo.
 *
 * @param iconResId El ID del recurso del ícono a mostrar.
 * @param buttonText El texto a mostrar debajo del ícono.
 * @param onClick La acción a realizar cuando se hace clic en el botón (por defecto, no hace nada).
 */
@Composable
fun ButtonWithIconAndText(iconResId: Int, buttonText: String, onClick: () -> Unit = {}) {
    // Columna que contiene el ícono y el texto del botón, con un clic para activar la acción.
    Column(
        modifier = Modifier
            .fillMaxWidth() // Ocupa todo el ancho disponible.
            .padding(horizontal = 16.dp) // Agrega un espacio horizontal de 16dp alrededor del botón.
            .clickable { onClick() }, // Hace que el botón sea cliclable y ejecuta la acción onClick().
        horizontalAlignment = Alignment.CenterHorizontally // Alineación horizontal al centro.
    ) {
        // Imagen del ícono del botón.
        Image(
            painter = painterResource(id = iconResId), // Recurso del ícono a mostrar.
            contentDescription = null, // Descripción del contenido (no necesaria para los íconos).
            modifier = Modifier
                .fillMaxWidth(0.5f) // Ocupa el 50% del ancho disponible.
                .aspectRatio(1.5f) // Mantiene una relación de aspecto de 1.5:1.
        )
        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre el ícono y el texto.
        // Texto del botón.
        Text(text = buttonText) // Texto a mostrar debajo del ícono.
    }
}





// Pantalla para visualizar el abecedario de señas
/**
 * Pantalla que muestra un carrusel de imágenes del abecedario y botones para navegar por ellas.
 */
@Composable
fun AbcScreen() {
    // Obtiene los nombres de las imágenes del abecedario
    val imaNames = stringArrayResource(id = R.array.ima_names)
    val context = LocalContext.current
    val icons = imaNames.map { iconName ->
        val resourceId = context.resources.getIdentifier(iconName, "drawable", context.packageName)
        resourceId
    }

    // Estado para mantener el índice de la imagen actual en el carrusel
    var currentIndex by remember { mutableStateOf(0) }

    // Columna principal que contiene el carrusel y los botones de navegación
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa todo el espacio disponible.
            .padding(16.dp), // Agrega un espacio de 16dp alrededor de la columna.
        horizontalAlignment = Alignment.CenterHorizontally, // Alineación horizontal al centro.
        verticalArrangement = Arrangement.Center // Alineación vertical al centro.
    ) {
        // Texto de bienvenida
        Text(
            text = stringResource(R.string.Carruce), // Texto obtenido de los recursos.
            style = MaterialTheme.typography.headlineSmall, // Estilo de texto.
            textAlign = TextAlign.Center, // Alineación del texto al centro.
            modifier = Modifier.padding(bottom = 25.dp) // Espacio inferior de 16dp.
        )

        if (icons.isNotEmpty()) {
            // Carrusel de imágenes del abecedario
            Card(
                modifier = Modifier
                    .fillMaxWidth() // Ocupa todo el ancho disponible.
                    .aspectRatio(1.5f) // Relación de aspecto de la tarjeta para que se adapte a diferentes tamaños de pantalla.
                    .padding(16.dp), // Agrega un espacio de 16dp alrededor de la tarjeta.
                shape = MaterialTheme.shapes.medium, // Forma de la tarjeta.
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) // Elevación de la tarjeta.
            ) {
                Image(
                    painter = painterResource(id = icons[currentIndex]), // Imagen actual en el carrusel.
                    contentDescription = null, // Descripción de la imagen (no necesario para las imágenes del abecedario).
                    modifier = Modifier
                        .fillMaxWidth() // Ocupa todo el ancho disponible.
                        .aspectRatio(1.5f), // Relación de aspecto para la imagen.
                    contentScale = ContentScale.Crop // Escala de contenido para ajustar la imagen.
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre el carrusel y los botones de navegación.

            // Botones para navegar por las imágenes
            Row(
                modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho disponible.
                horizontalArrangement = Arrangement.SpaceBetween // Espacio entre los botones.
            ) {
                // Botón para retroceder a la imagen anterior
                Button(onClick = { currentIndex = (currentIndex - 1 + icons.size) % icons.size }) {
                    Text(text = stringResource(R.string.retroceder)) // Texto obtenido de los recursos.
                }

                // Botón para avanzar a la siguiente imagen
                Button(onClick = { currentIndex = (currentIndex + 1) % icons.size }) {
                    Text(text = stringResource(R.string.siguiente)) // Texto obtenido de los recursos.
                }
            }
        } else {
            // Mensaje de que no hay imágenes disponibles
            Text(text = "No hay imágenes disponibles.")
        }
    }
}



// Pantalla para seleccionar categorías
/**
 * Pantalla que muestra una lista de categorías como botones que redirigen a diferentes secciones de la aplicación.
 *
 * @param navController El controlador de navegación para redirigir a las diferentes secciones.
 */
@Composable
fun CategoriesScreen(navController: NavHostController) {
    // Columna que contiene los botones de categoría con espaciado entre ellos.
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa todo el espacio disponible.
            .padding(16.dp), // Agrega un espacio de 16dp alrededor de la columna.
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espaciado vertical entre los elementos de la columna.
    ) {
        // Botón para la categoría "Comida" que redirige a la pantalla de comida.
        CategoryButton(
            text = stringResource(R.string.comida), // Texto del botón obtenido de los recursos.
            onClick = { navController.navigate("Comida") } // Acción al hacer clic en el botón.
        )
        // Botón para la categoría "Semana" que redirige a la pantalla de semana.
        CategoryButton(
            text = stringResource(R.string.semana), // Texto del botón obtenido de los recursos.
            onClick = { navController.navigate("Semana") } // Acción al hacer clic en el botón.
        )
        // Botón para la categoría "Meses del año" que redirige a la pantalla de meses.
        CategoryButton(
            text = stringResource(R.string.meses_del_a_o), // Texto del botón obtenido de los recursos.
            onClick = { navController.navigate("mes") } // Acción al hacer clic en el botón.
        )
    }
}

// Botón de categoría
/**
 * Composable que muestra un botón para una categoría específica.
 *
 * @param text El texto a mostrar en el botón.
 * @param onClick La acción a realizar cuando se hace clic en el botón.
 */
@Composable
fun CategoryButton(text: String, onClick: () -> Unit) {
    // Muestra un botón con el texto proporcionado y ocupa todo el ancho disponible.
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Muestra el texto del botón.
        Text(text = text)
    }
}




// Pantalla para mostrar la lista de comida
/**
 * Composable que muestra la pantalla principal de la sección de comida, que incluye una lista de comidas.
 *
 * @param favoritesViewModel El ViewModel utilizado para gestionar los elementos favoritos.
 */
// Composable que muestra la pantalla principal de la sección de comidas, que incluye una lista de comidas.
// favoritesViewModel: El ViewModel utilizado para gestionar los elementos favoritos.
@Composable
fun ComidaScreen(favoritesViewModel: FavoritesViewModel) {
    // Carga la lista de comidas desde el origen de datos.
    val comidasList = Datasource().cargaComida()

    // Muestra la lista de comidas utilizando el Composable ComidasList.
    ComidasList(comidasList, favoritesViewModel)
}

// Composable que muestra una lista de comidas.
// comidasList: La lista de comidas a mostrar.
// favoritesViewModel: El ViewModel utilizado para gestionar los elementos favoritos.
// modifier: Modificador opcional para personalizar la apariencia de la lista.
@Composable
fun ComidasList(comidasList: List<Comida>, favoritesViewModel: FavoritesViewModel, modifier: Modifier = Modifier) {
    // Utiliza LazyColumn para mostrar una lista de elementos de manera eficiente.
    LazyColumn(modifier = modifier) {
        // Itera sobre la lista de comidas y muestra cada una utilizando ComidaCard.
        items(comidasList) { comida ->
            // Llama al Composable ComidaCard para mostrar cada elemento de la lista.
            ComidaCard(comida = comida, favoritesViewModel = favoritesViewModel, modifier = Modifier.padding(20.dp))
        }
    }
}

// Composable que muestra una tarjeta para una comida específica.
// comida: El objeto Comida que representa la comida a mostrar en la tarjeta.
// favoritesViewModel: El ViewModel utilizado para gestionar los elementos favoritos.
// modifier: Modificador opcional para personalizar la apariencia de la tarjeta.
@Composable
fun ComidaCard(comida: Comida, favoritesViewModel: FavoritesViewModel, modifier: Modifier = Modifier) {
    // Variable que indica si la comida actual es favorita.
    var isFavorite by remember { mutableStateOf(favoritesViewModel.isFavorite(comida.imageResourceId)) }

    // Crea una tarjeta que muestra la imagen y el nombre de la comida.
    Card(
        modifier = modifier.clickable {}
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(30.dp), // Agrega sombra a la tarjeta con una elevación de 8dp.
        shape = RoundedCornerShape(16.dp) // Forma redondeada para las esquinas de la tarjeta.
    ) {
        Column {
            // Muestra la imagen de la comida.
            Image(
                painter = painterResource(comida.imageResourceId),
                contentDescription = stringResource(comida.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp),
                contentScale = ContentScale.Crop
            )
            // Muestra el nombre de la comida.
            Text(
                text = stringResource(comida.stringResourceId),
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            // Botón de favoritos que permite añadir o quitar la comida de la lista de favoritos.
            IconButton(
                onClick = {
                    isFavorite = !isFavorite
                    if (isFavorite) {
                        favoritesViewModel.addFavorite(comida.imageResourceId)
                    } else {
                        favoritesViewModel.removeFavorite(comida.imageResourceId)
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                // Icono de favoritos que cambia según si la comida es favorita o no.
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favoritos",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}







// Pantalla para mostrar la lista de días de la semana
/**
 * Composable que muestra la pantalla principal de la sección de semana, que incluye una lista de semanas.
 *
 * @param favoritesViewModel El ViewModel utilizado para gestionar los elementos favoritos.
 */
// Composable que muestra la pantalla principal de la sección de semanas, que incluye una lista de semanas.
// favoritesViewModel: El ViewModel utilizado para gestionar los elementos favoritos.
@Composable
fun SemanaScreen(favoritesViewModel: FavoritesViewModel) {
    // Carga la lista de elementos 'semana' desde el origen de datos.
    val semanaList = Datasource().cargaSemana()
    // Muestra la lista de elementos 'semana' utilizando el Composable SemanaList.
    SemanaList(semanaList, favoritesViewModel)
}

// Composable que muestra una lista de semanas.
// semanaList: La lista de semanas a mostrar.
// favoritesViewModel: El ViewModel utilizado para gestionar los elementos favoritos.
// modifier: Modificador opcional para personalizar la apariencia de la lista.
@Composable
fun SemanaList(semanaList: List<Semana>, favoritesViewModel: FavoritesViewModel, modifier: Modifier = Modifier) {
    // Utiliza LazyColumn para mostrar una lista de elementos de manera eficiente.
    LazyColumn(modifier = modifier) {
        // Itera sobre la lista de semanas y muestra cada una utilizando SemanaCard.
        items(semanaList) { semana ->
            // Llama al Composable SemanaCard para mostrar cada elemento de la lista.
            SemanaCard(semana = semana, favoritesViewModel = favoritesViewModel, modifier = Modifier.padding(20.dp))
        }
    }
}

// Composable que muestra una tarjeta para una semana específica.
// semana: El objeto Semana que representa la semana a mostrar en la tarjeta.
// favoritesViewModel: El ViewModel utilizado para gestionar los elementos favoritos.
// modifier: Modificador opcional para personalizar la apariencia de la tarjeta.
@Composable
fun SemanaCard(semana: Semana, favoritesViewModel: FavoritesViewModel, modifier: Modifier = Modifier) {
    // Variable que indica si la semana actual es favorita.
    var isFavorite by remember { mutableStateOf(favoritesViewModel.isFavorite(semana.imageResourceId)) }

    // Crea una tarjeta que muestra la imagen y el nombre de la semana.
    Card(
        modifier = modifier.clickable {}
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(30.dp), // Agrega sombra a la tarjeta con una elevación de 8dp.
        shape = RoundedCornerShape(16.dp) // Forma redondeada para las esquinas de la tarjeta.
    ) {
        Column {
            // Muestra la imagen de la semana.
            Image(
                painter = painterResource(semana.imageResourceId),
                contentDescription = stringResource(semana.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.Crop
            )
            // Muestra el nombre de la semana.
            Text(
                text = stringResource(semana.stringResourceId),
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            // Botón de favoritos que permite añadir o quitar la semana de la lista de favoritos.
            IconButton(
                onClick = {
                    isFavorite = !isFavorite
                    if (isFavorite) {
                        favoritesViewModel.addFavorite(semana.imageResourceId)
                    } else {
                        favoritesViewModel.removeFavorite(semana.imageResourceId)
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                // Icono de favoritos que cambia según si la semana es favorita o no.
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favoritos",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}





//Pantalla de Mes
/**
 * Composable que muestra la pantalla principal de la sección de meses, que incluye una lista de meses.
 *
 * @param favoritesViewModel El ViewModel utilizado para gestionar los elementos favoritos.
 */
@Composable
// Composable que muestra la pantalla principal de la sección de meses, que incluye una lista de meses.
// favoritesViewModel: El ViewModel utilizado para gestionar los elementos favoritos.
fun MesScreen(favoritesViewModel: FavoritesViewModel) {
    // Carga la lista de meses desde el origen de datos.
    val mesList = Datasource().cargaMeses()
    // Muestra la lista de meses utilizando el composable MesList.
    MesList(mesList, favoritesViewModel)
}

// Composable que muestra una lista de meses.
// mesList: La lista de meses a mostrar.
// favoritesViewModel: El ViewModel utilizado para gestionar los elementos favoritos.
// modifier: Modificador opcional para personalizar la apariencia de la lista.
@Composable
fun MesList(mesList: List<Mes>, favoritesViewModel: FavoritesViewModel, modifier: Modifier = Modifier) {
    // Crea una LazyColumn que muestra los elementos de la lista de meses.
    LazyColumn(modifier = modifier) {
        // Itera sobre la lista de meses y crea un MesCard para cada uno.
        items(mesList) { mes ->
            MesCard(
                mes = mes,
                favoritesViewModel = favoritesViewModel,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}

// Composable que muestra una tarjeta para un mes específico.
// mes: El objeto Mes que representa el mes a mostrar en la tarjeta.
// favoritesViewModel: El ViewModel utilizado para gestionar los elementos favoritos.
// modifier: Modificador opcional para personalizar la apariencia de la tarjeta.
@Composable
fun MesCard(mes: Mes, favoritesViewModel: FavoritesViewModel, modifier: Modifier = Modifier) {
    // Variable que indica si el mes actual es favorito.
    var isFavorite by remember { mutableStateOf(favoritesViewModel.isFavorite(mes.imageResourceId)) }

    // Crea una tarjeta que muestra la imagen y el nombre del mes.
    Card(
        modifier = modifier.clickable {}
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(30.dp), // Agrega sombra a la tarjeta con una elevación de 8dp.
        shape = RoundedCornerShape(16.dp) // Forma redondeada para las esquinas de la tarjeta.
    ) {
        Column {
            // Muestra la imagen del mes.
            Image(
                painter = painterResource(mes.imageResourceId),
                contentDescription = "Imagen del mes de ${stringResource(mes.stringResourceId)}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(448.dp),
                contentScale = ContentScale.Crop
            )
            // Muestra el nombre del mes.
            Text(
                text = stringResource(mes.stringResourceId),
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            // Botón de favoritos que permite añadir o quitar el mes de la lista de favoritos.
            IconButton(
                onClick = {
                    isFavorite = !isFavorite
                    if (isFavorite) {
                        favoritesViewModel.addFavorite(mes.imageResourceId)
                    } else {
                        favoritesViewModel.removeFavorite(mes.imageResourceId)
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                // Icono de favoritos que cambia según si el mes es favorito o no.
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favoritos",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}



//Pantalla de favoritos
// Define un Composable para la pantalla de favoritos.
@Composable
fun FavoritesScreen(favoritesViewModel: FavoritesViewModel) {
    // Recolecta el estado actual de los favoritos desde el ViewModel y lo observa.
    val favorites by favoritesViewModel.favorites.collectAsState()

    // Column como layout principal que contiene todos los elementos de la pantalla.
    Column(modifier = Modifier.padding(16.dp)) {
        // Texto que muestra un mensaje de bienvenida en la parte superior de la pantalla.
        Text(
            text = stringResource(R.string.bienvenidos_a_favoritos),
            style = MaterialTheme.typography.headlineMedium, // Define el estilo del texto usando el tema.
            modifier = Modifier.padding(bottom = 16.dp) // Agrega un padding abajo para separación.
        )

        // Condición que revisa si la lista de favoritos está vacía.
        if (favorites.isEmpty()) {
            // Texto que se muestra cuando no hay elementos en la lista de favoritos.
            Text(
                text = stringResource(R.string.no_hay_favoritos),
                style = MaterialTheme.typography.bodyMedium, // Define el estilo del texto.
                modifier = Modifier.padding(bottom = 16.dp) // Agrega un padding abajo.
            )
        } else {
            // LazyColumn utilizado para una lista perezosa de elementos favoritos.
            LazyColumn {
                // Itera sobre la lista de favoritos y crea una tarjeta para cada uno.
                items(favorites) { resourceId ->
                    FavoriteCard(resourceId = resourceId, favoritesViewModel = favoritesViewModel)
                }
            }
        }
    }
}

//Muestra las tarjetas de favoritos agregados a favoritos
// Define un Composable que crea una tarjeta para mostrar y gestionar un elemento como favorito.
@Composable
fun FavoriteCard(resourceId: Int, favoritesViewModel: FavoritesViewModel, modifier: Modifier = Modifier) {
    // Utiliza remember y mutableStateOf para mantener y gestionar el estado del ícono de favorito.
    var isFavorite by remember { mutableStateOf(favoritesViewModel.isFavorite(resourceId)) }

    // Composable Card que utiliza el modifier proporcionado, aquí se puede añadir más comportamiento al clic, si necesario.
    Card(modifier = modifier
        .clickable {}
        .padding(16.dp),
        elevation = CardDefaults.cardElevation(30.dp), // Agrega sombra a la tarjeta con una elevación de 8dp.
        shape = RoundedCornerShape(16.dp) // Forma redondeada para las esquinas de la tarjeta.
    )  {
        // Column para organizar los elementos verticalmente dentro de la tarjeta.
        Column {
            // Composable Image para mostrar una imagen del recurso especificado.
            Image(
                painter = painterResource(resourceId),
                contentDescription = null, // contentDescription se establece como null porque la imagen es decorativa.
                modifier = Modifier
                    .fillMaxWidth() // Ajusta la imagen para que ocupe el ancho máximo disponible.
                    .height(448.dp), // Establece una altura fija para la imagen.
                contentScale = ContentScale.Crop // Escala el contenido para llenar el tamaño del contenedor recortando.
            )
            // IconButton para manejar la acción de marcar como favorito o no.
            IconButton(
                onClick = {
                    // Cambia el estado de isFavorite cada vez que se hace clic en el botón.
                    isFavorite = !isFavorite
                    // Lógica para añadir o remover el recurso de los favoritos usando el ViewModel.
                    if (isFavorite) {
                        favoritesViewModel.addFavorite(resourceId)
                    } else {
                        favoritesViewModel.removeFavorite(resourceId)
                    }
                },
                modifier = Modifier.align(Alignment.End) // Alinea el botón de favoritos al final de la columna.
            ) {
                // Icono que cambia visualmente según si el ítem es favorito o no.
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favoritos", // Descripción accesible para el icono.
                    tint = Color.Red // Establece el color del ícono.
                )
            }
        }
    }
}



//Pantalla de Busqueda que muestra resultados de las listas
//Comida, Semana y Mes
@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    var query by remember { mutableStateOf("") }
    val results = remember { mutableStateOf(listOf<Any>()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = {
                query = it
                results.value = viewModel.search(query)
            },
            label = { Text(stringResource(R.string.qu_deseas_buscar)) },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(results.value) { item ->
                when (item) {
                    is Comida -> ComidaCard(comida = item, favoritesViewModel = viewModel())
                    is Semana -> SemanaCard(semana = item, favoritesViewModel = viewModel())
                    is Mes -> MesCard(mes = item, favoritesViewModel = viewModel())
                }
            }
        }
    }
}



//Barra de navegacion
// Composable function que define la barra de navegación inferior
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // Lista de elementos que se mostrarán en la barra de navegación inferior
    val items = listOf(
        // Define un elemento de la barra de navegación para la página principal
        BottomNavItem("Home", Icons.Default.Home, stringResource(R.string.home)),
        // Define un elemento de la barra de navegación para la página de favoritos
        BottomNavItem("Favorites", Icons.Default.Favorite, stringResource(R.string.favoritos)),
        // Define un elemento de la barra de navegación para la página de búsqueda
        BottomNavItem("Search", Icons.Default.Search, stringResource(R.string.search))
    )

    // Composable que crea una barra de navegación
    NavigationBar {
        // Observa la entrada actual en la pila de navegación y almacena el estado del destino actual
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        // Itera sobre cada elemento en la lista de items
        items.forEach { item ->
            // Crea un ítem de navegación en la barra con un ícono y etiqueta
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                // Determina si el ítem está seleccionado comparando la ruta del destino actual con la ruta del ítem
                selected = currentDestination?.route == item.route,
                // Define la acción a ejecutar cuando se hace clic en el ítem
                onClick = {
                    // Navega a la ruta especificada en el ítem si no es la misma que la actual
                    if (currentDestination?.route != item.route) {
                        navController.navigate(item.route) {
                            // Evita crear múltiples entradas en la pila para la misma pantalla
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}




//Para la funcionalidad de la barra de navegacion
/**
 * Clase de datos que representa un elemento de navegación para la barra de navegación inferior.
 * @param route La ruta a la que se navegará cuando se seleccione este elemento.
 * @param icon El icono utilizado para este elemento de navegación.
 * @param title El título del elemento de navegación.
 */
data class BottomNavItem(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val title: String)



/**
 * Previsualización de la aplicación en un entorno de diseño.
 */
@Preview(showBackground = true)
@Composable
fun LenguajePreview() {
    // Obtiene una instancia de FavoritesViewModel
    val favoritesViewModel: FavoritesViewModel = viewModel()

    // Muestra la aplicación en el tema LenguajeSeasTheme
    LenguajeSeasTheme {
        // Muestra la navegación de la aplicación con el ViewModel de favoritos
        AppNavigation(favoritesViewModel)
    }
}
