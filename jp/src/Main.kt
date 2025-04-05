
import java.time.LocalDate
import java.time.format.DateTimeFormatter
data class Property (
    val id: Int,
    val type: String,
    val location: String,
    val rent: Double,
    val owner: Owner,
    var renter: Renter? = null
)
data class Owner(
    val name: String,
    val contact: String
)
data class Renter(
    val name: String,
    val contact: String,
    val leaseStart: LocalDate,
    val leaseEnd: LocalDate,
    var rentPaid: Boolean = false
)

fun main() {
    val rentalSystem = HomeRentalSystem()

    while (true) {

        println("\n HOME RENTAL SYSTEM")
        println("1. Add Property")
        println("2. Remove Property")
        println("3. Search Property")
        println("4. Update Property")
        println("5. Assign Renter & Lease")
        println("6. Record Rent Payment")
        println("7. List All Properties")
        println("8. Exit")
        print("Choose an option: ")

        when (readln().toIntOrNull()) {
            1 -> {
                print("Enter Property ID: ")
                val id = readln().toInt()
                if (id < 0) {
                    println("Cannot add negative")
                    continue
                } else
                {
                    readln().toInt()
                }

                print("Enter Property Type (e.g., Apartment, Villa): ")
                val type = readln()
                print("Enter Location: ")
                val location = readln()
                print("Enter Rent Amount: ")
                val rent = readln().toDoubleOrNull() ?: return
                print("Enter Owner Name: ")
                val ownerName = readln()
                print("Enter Owner Contact: ")
                val ownerContact = readln()
                rentalSystem.addProperty(id, type, location, rent, ownerName, ownerContact)
            }
            2 -> {

                rentalSystem.removeProperty()
            }
            3 -> {

                rentalSystem.searchProperty()
            }
            4 -> {


                rentalSystem.updateProperty()

            }
            5 -> {


                rentalSystem.assignRenter()
            }
            6 -> {

                rentalSystem.recordPayment()
            }
            7 -> rentalSystem.listProperties()
            8 -> {
                println("Exiting... Thank you!")
                return
            }
            else -> println("Invalid option. Please try again.")
        }
    }
}

class HomeRentalSystem {
    private val properties = HashMap<Int, Property>()

    fun addProperty(id: Int, type: String, location: String, rent: Double, ownerName: String, ownerContact: String) {
        if (properties.containsKey(id) ) {

            println("Property with ID $id already exists!")



        } else {
            properties[id] = Property(id, type, location, rent, Owner(ownerName, ownerContact))

           println(" Property ID $id added successfully!")

        }
    }

    fun removeProperty() {
        if (properties.isNotEmpty()) {
            print("Enter Property ID to Remove: ")
            val idInput = readln()
            val id = idInput.toIntOrNull() ?: return
            print("Are you sure you want to remove property ID $id?(Yes or No):")
            val confirmation = readln().lowercase()
            if (confirmation == "yes") {
                if (properties.containsKey(id)) {
                    val removedProperty = properties.remove(id)
                    println("Property ID $id removed successfully. Removed data: $removedProperty")
                } else {
                    println("Property ID $id not found!")
                }
            } else {
                println("Property removal cancelled.")
            }
        }else{
            println("The list is empty")


        }
    }

    fun searchProperty() {
        if (properties.isNotEmpty()) {
            print("Enter Property ID to Search: ")
            val id = readln().toIntOrNull() ?: return
            val property = properties[id]
            if (property != null) {
                println(" Property Found: $property")
            } else {
                println(" Property ID $id not found!")
            }
        }else{
            println("The list is empty")
        }
    }

    fun updateProperty() {


        if (properties.isNotEmpty()) {
            print("Enter Property ID to Update: ")
            val id = readln().toIntOrNull() ?: return
            print("Enter New Property Type: ")
            val type = readln()
            print("Enter New Location: ")
            val location = readln()
            print("Enter New Rent Amount: ")
            val rent = readln().toDoubleOrNull() ?: return
            print("Enter New Owner Name: ")
            val ownerName = readln()
            print("Enter New Owner Contact: ")
            val ownerContact = readln()



            if (properties.containsKey(id)) {
                properties[id] =
                    Property(id, type, location, rent, Owner(ownerName, ownerContact), properties[id]?.renter)
                println(" Property ID $id updated successfully!")
            } else {
                println(" Property ID $id not found!")
            }
        } else {
            println("Empty")
        }

    }

    fun assignRenter() {

        if (properties.isNotEmpty()) {
            print("Enter Property ID to Assign Renter: ")
            val id = readln().toIntOrNull() ?: return
            print("Enter Renter Name: ")
            val renterName = readln()
            print("Enter Renter Contact: ")
            val renterContact = readln()
            print("Enter Lease Start Date (YYYY-MM-DD): ")
            val startDate = LocalDate.parse(readln(), DateTimeFormatter.ISO_DATE)
            print("Enter Lease End Date (YYYY-MM-DD): ")
            val endDate = LocalDate.parse(readln(), DateTimeFormatter.ISO_DATE)



            val property = properties[id]
            if (property != null) {
                property.renter = Renter(renterName, renterContact, startDate, endDate)
                println("✅ Renter $renterName assigned to Property ID $id from $startDate to $endDate")
            } else {
                println(" Property ID $id not found!")
            }
        }else{
            println("The list is empty")
        }
    }

    fun recordPayment() {
        if (properties.isNotEmpty()) {
            print("Enter Property ID to Record Payment: ")
            val id = readln().toIntOrNull() ?: return

            val property = properties[id]
            if ( property?.renter != null) {
                property.renter!!.rentPaid = true
                println("✅ Rent marked as paid for Property ID $id")
            } else {
                println(" Property ID $id not found or no renter assigned!")
            }
        }else{
            println("The list is empty")
        }
    }

    fun listProperties() {
        if (properties.isEmpty()) {
            println(" No properties available.")
        } else {
            println("\n Available Properties:")
            for ((_, property) in properties) {
                println("Address: ${property.location}")
                println("Property Type: ${property.type}")
                println("Id : ${property.id}")
            }
        }
    }
}





