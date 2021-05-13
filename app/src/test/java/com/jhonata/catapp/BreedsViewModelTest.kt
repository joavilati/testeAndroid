package com.jhonata.catapp

import androidx.lifecycle.SavedStateHandle
import com.jhonata.catapp.extensions.getOrAwaitValue
import com.jhonata.catapp.model.Breed
import com.jhonata.catapp.model.Image
import com.jhonata.catapp.repository.DefaultCatsRepository
import com.jhonata.catapp.retrofit.TheCatApi
import com.jhonata.catapp.util.Assertion
import com.jhonata.catapp.util.JSONFileNames
import com.jhonata.catapp.util.readJSON
import com.jhonata.catapp.util.readJSONRaw
import com.jhonata.catapp.viewmodel.BreedsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response

@ExperimentalCoroutinesApi
class BreedsViewModelTest: BaseTest() {
    private lateinit var viewModel: BreedsViewModel
    private val api: TheCatApi by lazy {
        mock(TheCatApi::class.java)
    }

    override fun setup() {
        super.setup()
        val repository = DefaultCatsRepository(api)
        val savedStateHandle = mock(SavedStateHandle::class.java)
        viewModel = BreedsViewModel(repository, savedStateHandle)
    }

    @Test
    fun `when request new breeds and are successful, it must return a list of 15 breeds`() = runBlocking {
        val response = Response.success(readJSON<List<Breed>>(JSONFileNames.BREEDS_JSON))

        val expectedValue = Assertion(15)

        `when`(api.getBreeds(anyInt(), anyInt(), anyString())).thenReturn(response)

        viewModel.getBreeds(0)

        expectedValue isEqualTo viewModel.breeds.getOrAwaitValue().size
    }

    @Test
    fun `when request new breeds and are successful, it must match with the expected mocked breeds`() = runBlocking {
        val response = Response.success(readJSON<List<Breed>>(JSONFileNames.TWO_BREEDS_JSON))

        val expectedBreeds = Assertion(breedListOfTwo)
        `when`(api.getBreeds(anyInt(), anyInt(), anyString())).thenReturn(response)

        viewModel.getBreeds(0)

        expectedBreeds isEqualTo viewModel.breeds.getOrAwaitValue().toList()
    }

    @Test
    fun `when request new breeds and request fail on page 0, it must feed live data error with FIRST enum`() = runBlocking {
        val response = Response.error<List<Breed>>(
            404,
            readJSONRaw(JSONFileNames.EMPTY_JSON).toResponseBody("application/json".toMediaTypeOrNull())
        )

        val expectedBreeds = Assertion(BreedsViewModel.TURN.FIRST)
        `when`(api.getBreeds(anyInt(), anyInt(), anyString())).thenReturn(response)

        viewModel.getBreeds(0)

        expectedBreeds isEqualTo viewModel.error.getOrAwaitValue()
    }

    @Test
    fun `when request new breeds and request fail on page 1, it must feed live data error with OTHERS enum`() = runBlocking {
        val response = Response.error<List<Breed>>(
            404,
            readJSONRaw(JSONFileNames.EMPTY_JSON).toResponseBody("application/json".toMediaTypeOrNull())
        )

        val expectedBreeds = Assertion(BreedsViewModel.TURN.OTHERS)
        `when`(api.getBreeds(anyInt(), anyInt(), anyString())).thenReturn(response)

        viewModel.getBreeds(1)

        expectedBreeds isEqualTo viewModel.error.getOrAwaitValue()
    }

    private val breedListOfTwo = listOf(
        Breed(
            name = "Abyssinian",
            origin = "Egypt",
            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            image = Image(
                width = 1204,
                height = 1445,
                url = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg"
            )
        ),
        Breed(
            name = "Aegean",
            origin = "Greece",
            temperament = "Affectionate, Social, Intelligent, Playful, Active",
            description = "Native to the Greek islands known as the Cyclades in the Aegean Sea, these are natural cats, meaning they developed without humans getting involved in their breeding. As a breed, Aegean Cats are rare, although they are numerous on their home islands. They are generally friendly toward people and can be excellent cats for families with children.",
            image = Image(
                width = 1200,
                height = 800,
                url = "https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg"
            )
        )
    )
}