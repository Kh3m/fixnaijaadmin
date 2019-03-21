package com.khemportfolio.fixnaijateam.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khemportfolio.fixnaijateam.R
import com.khemportfolio.fixnaijateam.adapter.Problem
import com.khemportfolio.fixnaijateam.adapter.ProblemAdapter
import kotlinx.android.synthetic.main.fragment_landing.*

class LandingFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var problemAdapter: ProblemAdapter
    lateinit var layoutManageer: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu( true )
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = rv_problem
        layoutManageer = LinearLayoutManager(view.context)
        problemAdapter = ProblemAdapter(
                listOf<Problem>(
                        Problem("4:43 PM", "This problem is critical please send your team", "+2348131915694", "khem6333@gmail.com"),
                        Problem("4:43 PM", "This problem is critical please send your team", "+2348131915694", "khem6333@gmail.com"),
                                Problem("4:43 PM", "This problem is critical please send your team", "+2348131915694", "khem6333@gmail.com")
                )
        )

        recyclerView.apply {
            adapter = problemAdapter
            layoutManager = layoutManageer
        }
    }
}