package com.mahmoudgalal.myphoneinvoice.domain.mappers

import com.mahmoudgalal.myphoneinvoice.common.Mapper
import javax.inject.Inject
import com.mahmoudgalal.myphoneinvoice.data.model.Invoice as DataInvoice
import com.mahmoudgalal.myphoneinvoice.domain.model.Invoice as DomainInvoice

class DomainToDataMapper: Mapper<List<DomainInvoice>,List<DataInvoice>> {
    override fun map(t: List<DomainInvoice>): List<DataInvoice> {
        return  t.map {
            DataInvoice(
                id = it.id,
                areaCode = it.areaCode,
                phoneNumber = it.phoneNumber,
                totalAmount = it.totalAmount,
                consumptionStart = it.consumptionStart?.let { cs ->
                    DataInvoice.DateStruct(
                        cs.Sec,
                        cs.Min, cs.Hour, cs.Day, cs.Month, cs.Year
                    )
                },
                consumptionEnd = it.consumptionEnd?.let { ce ->
                    DataInvoice.DateStruct(
                        ce.Sec,
                        ce.Min, ce.Hour, ce.Day, ce.Month, ce.Year
                    )
                },
                subscriptionStart = it.subscriptionStart?.let { ss ->
                    DataInvoice.DateStruct(
                        ss.Sec,
                        ss.Min, ss.Hour, ss.Day, ss.Month, ss.Year
                    )
                },
                subscriptionEnd = it.subscriptionEnd?.let { se ->
                    DataInvoice.DateStruct(
                        se.Sec,
                        se.Min, se.Hour, se.Day, se.Month, se.Year
                    )
                },
                paymentGateNameAr = it.paymentGateNameAr,
                status = it.status,
                details = it.details?.map { d ->
                    DataInvoice.Detail(
                        d.name,
                        d.value,
                        d.arabicName
                    )
                }
            )
        }
    }
}