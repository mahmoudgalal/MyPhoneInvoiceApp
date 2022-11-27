package com.mahmoudgalal.myphoneinvoice.domain.mappers

import com.mahmoudgalal.myphoneinvoice.data.model.Invoice
import com.mahmoudgalal.myphoneinvoice.common.Mapper
import javax.inject.Inject
import com.mahmoudgalal.myphoneinvoice.domain.model.Invoice as DomainInvoice


class DataToDomainMapper : Mapper<List<Invoice>, List<DomainInvoice>> {
    override fun map(t: List<Invoice>): List<DomainInvoice> {
        return  t.map {
            DomainInvoice(
                id = it.id,
                areaCode = it.areaCode,
                phoneNumber = it.phoneNumber,
                totalAmount = it.totalAmount,
                consumptionStart = it.consumptionStart?.let { cs ->
                    com.mahmoudgalal.myphoneinvoice.domain.model.Invoice.DateStruct(
                        cs.Sec,
                        cs.Min, cs.Hour, cs.Day, cs.Month, cs.Year
                    )
                },
                consumptionEnd = it.consumptionEnd?.let { ce ->
                    com.mahmoudgalal.myphoneinvoice.domain.model.Invoice.DateStruct(
                        ce.Sec,
                        ce.Min, ce.Hour, ce.Day, ce.Month, ce.Year
                    )
                },
                subscriptionStart = it.subscriptionStart?.let { ss ->
                    com.mahmoudgalal.myphoneinvoice.domain.model.Invoice.DateStruct(
                        ss.Sec,
                        ss.Min, ss.Hour, ss.Day, ss.Month, ss.Year
                    )
                },
                subscriptionEnd = it.subscriptionEnd?.let { se ->
                    com.mahmoudgalal.myphoneinvoice.domain.model.Invoice.DateStruct(
                        se.Sec,
                        se.Min, se.Hour, se.Day, se.Month, se.Year
                    )
                },
                paymentGateNameAr = it.paymentGateNameAr,
                status = it.status,
                details = it.details?.map { d ->
                    com.mahmoudgalal.myphoneinvoice.domain.model.Invoice.Detail(
                        d.name,
                        d.value,
                        d.arabicName
                    )
                }
            )
        }
    }
}