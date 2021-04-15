package com.jhonata.catapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.jhonata.catapp.ui.BreedListFragment
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    companion object {
        private var actualScreen = ActualFragment.LIST
        fun setActualFragment(fragment: ActualFragment) {
            actualScreen = fragment
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFragment()

    }

    private fun startFragment() {
        actualScreen = ActualFragment.LIST
        val fragment = BreedListFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fcv_main, fragment)
            .commit()
    }

    enum class ActualFragment{
        LIST,
        DETAIL
    }

    override fun onBackPressed() {
        when(actualScreen){
            ActualFragment.LIST -> finish()
            ActualFragment.DETAIL -> {
                supportFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    replace(R.id.fcv_main, BreedListFragment())
                    addToBackStack(null)
                }
            }
        }
    }

}
