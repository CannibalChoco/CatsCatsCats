package com.kglazuna.app.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kglazuna.app.catMock
import com.kglazuna.app.repository.CatRepo
import com.kglazuna.app.whenCall
import junit.framework.TestCase.*
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
class CatRatingViewModelTest {

    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var catRatingViewModel: CatRatingViewModel
    @Mock
    private lateinit var catRepo: CatRepo

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        whenCall(catRepo.catList).thenReturn(catMock)
        catRatingViewModel = CatRatingViewModel(catRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Gets cat list from repository on initialization`() = runBlockingTest {
        verify(catRepo, times(1)).catList
        assertEquals(catMock, catRatingViewModel.catList)
    }

    @Test
    fun `createVote returns correctly constructed Vote`() {
        val vote0 = catRatingViewModel.createVote(0, 0)
        val vote1 = catRatingViewModel.createVote(1, 1)

        // assert vote0 values
        assertEquals(catMock[0].id, vote0.image_id)
        assertEquals(0, vote0.value)

        // assert vote1 values
        assertEquals(catMock[1].id, vote1.image_id)
        assertEquals(1, vote1.value)
    }

    @Test
    fun `sendVote calls repository sendVote()`() = runBlockingTest {
        val vote = catRatingViewModel.createVote(0, 1)
        whenCall(catRepo.sendVote(vote)).thenReturn(true)

        catRatingViewModel.sendVote(vote)
        delay(100)
        verify(catRepo, times(1)).sendVote(vote)
    }

}
