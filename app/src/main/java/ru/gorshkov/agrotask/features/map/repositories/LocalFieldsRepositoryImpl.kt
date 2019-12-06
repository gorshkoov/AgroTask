package ru.gorshkov.agrotask.features.map.repositories

import com.google.android.gms.maps.model.LatLng
import ru.gorshkov.agrotask.R
import ru.gorshkov.agrotask.data.pojo.AgroField
import ru.gorshkov.agrotask.utils.DateUtils
import java.util.*

class LocalFieldsRepositoryImpl constructor(private val dateUtils: DateUtils) : FieldsRepository {

    override fun getYears(): List<String> {
        return getFields()
            .map { dateUtils.getYear(it.dateEnd) }
            .distinct()
    }

    override fun onSearchChanged(text: String): List<AgroField> {
        return getFields()
            .filter { it.name.toLowerCase(Locale.getDefault()).contains(text) }
    }

    override fun onFilterSelected(year: String?): List<AgroField> {
        return if (year.isNullOrEmpty()) {
            getFields()
        } else {
            getFields()
                .filter { dateUtils.getYear(it.dateEnd).contains(year) }
        }
    }

    override fun getFields(): List<AgroField> {
        val fields = mutableListOf<AgroField>()
        fields.add(
            AgroField(
                310101001,
                130,
                "Пшеница яровая",
                "Скипетр РС1",
                1429736400000,
                1442955600000,
                R.color.green_40,
                getFirstField()
            )
        )
        fields.add(
            AgroField(
                310101002,
                96,
                "Соя",
                "Кофу РС2",
                1431637200000,
                1444856400000,
                R.color.blue_40,
                getSecondField()
            )
        )
        fields.add(
            AgroField(
                310101003,
                212,
                "Сахарная свекла",
                "Крокодил F1",
                1457557200000,
                1476046800000,
                R.color.red_40,
                getThirdField()
            )
        )
        fields.add(
            AgroField(
                310101004,
                96,
                "Пшеница яровая",
                "Скипетр РС1",
                1459803600000,
                1478293200000,
                R.color.orange_40,
                getFourthField()
            )
        )
        fields.add(
            AgroField(
                310101005,
                110,
                "Кукуруза",
                "Скипетр РС3",
                1519851600000,
                1538341200000,
                R.color.yellow_40,
                getFifthField()
            )
        )
        fields.add(
            AgroField(
                310101006,
                300,
                "Подсолнухи желтые",
                "Скипетр РС4",
                1526590800000,
                1542488400000,
                R.color.violin_40,
                getSixthField()
            )
        )
        fields.add(
            AgroField(
                310101007,
                50,
                "Лен",
                "Скипетр РС5",
                1521666000000,
                1537563600000,
                R.color.pink_40,
                getSeventhField()
            )
        )

        return fields
    }

    private fun getFirstField(): List<LatLng> {
        return listOf(
            LatLng(61.80384840739244, 39.29752572015718),
            LatLng(61.768789522352435, 39.41562874750093),
            LatLng(61.834978305380716, 39.44309456781343),
            LatLng(61.904905227594, 39.36069710687593),
            LatLng(61.891967838990254, 39.17942269281343)
        )
    }

    private fun getSecondField(): List<LatLng> {
        return listOf(
            LatLng(61.79735903774938, 39.51450570062593),
            LatLng(61.742794189868945, 39.53098519281343),
            LatLng(61.742794189868945, 39.76169808343843),
            LatLng(61.81941730253335, 39.74521859125093)
        )
    }

    private fun getThirdField(): List<LatLng> {
        return listOf(
            LatLng(62.03011428809841, 39.61338265375093),
            LatLng(62.00820756072283, 39.77543099359468),
            LatLng(61.96047369035458, 39.73697884515718),
            LatLng(61.98241475021735, 39.53647835687593),
            LatLng(62.0378423106961, 39.50351937250093)
        )
    }

    private fun getFourthField(): List<LatLng> {
        return listOf(
            LatLng(61.86996172037123, 39.77817757562593),
            LatLng(61.84923562726734, 39.90177376703218),
            LatLng(61.91783714634502, 39.91276009515718)
        )
    }

    private fun getFifthField(): List<LatLng> {
        return listOf(
            LatLng(61.75319495756062, 39.92099984125093),
            LatLng(61.828495513144205, 39.98417122796968),
            LatLng(61.81941730253335, 40.12424691156343),
            LatLng(61.755794600533115, 40.23136361078218),
            LatLng(61.72588544747111, 40.14621956781343)
        )
    }

    private fun getSixthField(): List<LatLng> {
        return listOf(
            LatLng(62.05972771569589, 39.95945198968843),
            LatLng(61.97725356777783, 39.96769173578218),
            LatLng(62.00691843868552, 40.28080208734468),
            LatLng(62.08031135584757, 40.14621956781343),
            LatLng(62.07645298575181, 39.99241097406343)
        )
    }

    private fun getSeventhField(): List<LatLng> {
        return listOf(
            LatLng(61.91913003744191, 39.51175911859468),
            LatLng(61.88808555554062, 39.53373177484468),
            LatLng(61.85830501784389, 39.62711556390718),
            LatLng(61.90878537747861, 39.61612923578218),
            LatLng(61.92947119786394, 39.58042366937593)
        )
    }
}