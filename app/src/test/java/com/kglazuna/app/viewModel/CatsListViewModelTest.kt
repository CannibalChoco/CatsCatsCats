package com.kglazuna.app.viewModel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kglazuna.app.catMock
import com.kglazuna.app.repository.CatRepo
import com.kglazuna.app.whenCall
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@ObsoleteCoroutinesApi
@PowerMockIgnore("javax.net.ssl.*")
@ExperimentalCoroutinesApi
@RunWith(PowerMockRunner::class)
@PrepareForTest(
    CatRepo::class
)
class CatsListViewModelTest {

    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var catsListViewModel: CatsListViewModel
    @Mock
    private lateinit var catRepo: CatRepo

    @Before
    fun setup() {
        catsListViewModel = CatsListViewModel(catRepo)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCats() calls repository and assigns value to catList`() = runBlockingTest {
        whenCall(catRepo.getCats()).thenReturn(catMock)

        catsListViewModel.getCats()

        verify(catRepo, times(1)).getCats()
        assertEquals(catMock, catsListViewModel.catList.value)
    }

}
