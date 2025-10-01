package com.morbidelli.morbidelli_design

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TermsOfServiceActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var rvTerms: RecyclerView
    private lateinit var termsAdapter: TermsAdapter

    private val terms = listOf(
        TermsItem(
            id = "1",
            title = "1. Rental Agreement",
            content = "By using MotoRent services, you agree to rent motorbikes under the terms specified in your booking confirmation. All rentals are subject to availability and our approval process."
        ),
        TermsItem(
            id = "2",
            title = "2. Driver Requirements",
            content = "Renters must be at least 21 years old with a valid motorcycle license. International visitors must provide an International Driving Permit along with their home country license."
        ),
        TermsItem(
            id = "3",
            title = "3. Security Deposit",
            content = "A security deposit is required for all rentals. The amount varies by motorcycle model and rental duration. Deposits are refunded within 3-5 business days after the safe return of the vehicle."
        ),
        TermsItem(
            id = "4",
            title = "4. Insurance Coverage",
            content = "Basic insurance is included with all rentals. Additional coverage options are available. Renters are responsible for any damages not covered by insurance."
        ),
        TermsItem(
            id = "5",
            title = "5. Cancellation Policy",
            content = "Cancellations made 24+ hours before pickup receive a full refund. Cancellations within 24 hours are subject to a 50% cancellation fee."
        ),
        TermsItem(
            id = "6",
            title = "6. Prohibited Uses",
            content = "Motorcycles may not be used for racing, off-road riding, carrying passengers exceeding the bike's capacity, or any illegal activities. Violations may result in immediate termination of rental agreement."
        ),
        TermsItem(
            id = "7",
            title = "7. Vehicle Condition",
            content = "Renters must inspect the vehicle before departure and report any existing damage. Failure to report pre-existing damage may result in charges for repairs."
        ),
        TermsItem(
            id = "8",
            title = "8. Return Policy",
            content = "Vehicles must be returned on time and in the same condition as received. Late returns are subject to additional charges. Fuel must be returned at the same level as pickup."
        ),
        TermsItem(
            id = "9",
            title = "9. Liability",
            content = "Renters are fully responsible for the vehicle during the rental period. MotoRent is not liable for any accidents, injuries, or damages that occur during the rental period."
        ),
        TermsItem(
            id = "10",
            title = "10. Privacy Policy",
            content = "We collect and process personal information in accordance with our privacy policy. By using our services, you consent to the collection and use of your information as described in our privacy policy."
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_service)
        
        initViews()
        setupRecyclerView()
        setupClickListeners()
    }
    
    private fun initViews() {
        btnBack = findViewById(R.id.btn_back)
        rvTerms = findViewById(R.id.rv_terms)
    }
    
    private fun setupRecyclerView() {
        termsAdapter = TermsAdapter(terms)
        
        rvTerms.layoutManager = LinearLayoutManager(this)
        rvTerms.adapter = termsAdapter
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
    }
}
