package com.example.Varsani.utils;

public class Urls {


   public static String ipAddress = "http://102.217.148.180/lotusfertility/";
    //public static String ipAddress = "http://192.168.99.205/lotusfertility/";

    private static final String ROOT_URL =ipAddress+ "android_files/";
    public static final String ROOT_URL_IMAGES =ipAddress+"upload_products/";
    //public static final String ROOT_URL_UPLOADS =ipAddress+"client/uploads/" ;
    public static final String ROOT_URL_AGREEMENTS =ROOT_URL+ "upload_agreements/";
    public static  final String URL_PRINT=ipAddress+"print_pdf.php";
    public static  final String UEL_FEEDBACK=ROOT_URL+"client/get_feedback.php";
    public static  final String UEL_FEEDBACK_SEND=ROOT_URL+"client/send_feedback.php";
    public static  final String ROOT_URL_UPLOADS=ROOT_URL+"client/uploads/";


    public static  final String UEL_STAFF_SEND_FEEDBACK=ROOT_URL+"client/staff_sendfeedback.php";
    public static  final String UEL_STAFF_FEEDBACK=ROOT_URL+"client/getstafffeedback.php";

     //  surrogates
    public static final String URL_GET_SURROGATES=ROOT_URL + "client/surrogates.php";
    public static final String URL_ADD_CART=ROOT_URL + "client/add_to_cart.php";
    public static final String URL_GET_CART=ROOT_URL + "client/cart.php";
    public static final String URL_UPDATE_CART=ROOT_URL + "client/car_update.php";
    public static final String URL_REMOVE_ITEM=ROOT_URL + "client/cart_remove.php";

    //services
    public static final String URL_GET_DONORS=ROOT_URL + "client/donors.php";
    public static final String URL_ADD_CART2=ROOT_URL + "client/add_to_cart2.php";
    public static final String URL_GET_CART2=ROOT_URL + "client/cart2.php";
    public static final String URL_REMOVE_BOOKING=ROOT_URL + "client/booking_remove.php";
    public static final String URL_SUBMIT_REQUEST = ROOT_URL+"client/submit_request.php";

    // shipping
    public static final String URL_GET_COUNTIES=ROOT_URL + "client/counties.php";
    public static final String URL_GET_TOWNS=ROOT_URL + "client/towns.php";
    public static final String URL_DELIVERY_DETAILS=ROOT_URL + "client/delivery_details.php";

    // checkout
    public static final String URL_GET_CHECKOUT_TOTAL=ROOT_URL + "client/checkout_cost.php";
    // user
    public static final String URL_REG = ROOT_URL +"client/register.php";
    public static final String URL_LOGIN= ROOT_URL+"client/login.php";
    public static final String URL_RESET = ROOT_URL + "client/forgotpass.php";
    public static final String URL_RESET2 = ROOT_URL + "client/resetpass.php";
//    SUPPLIERS
    public static final String URL_REG_SUPPLIERS= ROOT_URL+"supplier/reg_supplier.php";
    public static final String URL_MY_REQUESTS= ROOT_URL+"supplier/my_requests.php";
    public static final String URL_ACCEPT= ROOT_URL+"supplier/approve_items.php";
   // orders
    public static final String URL_SUBMIT_APPLICATION = ROOT_URL+"client/submit_application.php";
    public static final String URL_GET_ORDER_ITEMS= ROOT_URL+"client/order_items.php";
    public static final String URL_GET_ORDER_ITEMS2= ROOT_URL+"client/order_items2.php";
    public static final String URL_MARK_DELIVERED= ROOT_URL+"client/mark_delivered.php";
    public static final String URL_MARK_REJECTED= ROOT_URL+"client/mark_rejected.php";
    public static final String URL_MARK_COMPLETE= ROOT_URL+"client/mark_completed.php";

    //invoices
    public static final String URL_SUBMIT_INVOICE= ROOT_URL+"intended_parent/submit_invoice.php";

    //Staff
    public static final String URL_STAFF_LOGIN=ROOT_URL + "staff_login.php";
    //Finance
    public static final String URL_NEW_ORDERS=ROOT_URL + "finance/new_orders.php";
    public static final String URL_GET_CLIENT_ITEMS=ROOT_URL + "client_item.php";
    public static final String URL_GET_APPROVE_ORDERS=ROOT_URL + "finance/approve_order.php";
    public static final String URL_APPROVED_ORDERS=ROOT_URL + "finance/approved_orders.php";
    public static final String URL_NEW_SERV_PAYMENTS=ROOT_URL + "finance/new_serv_payments.php";
    public static final String URL_APPROVE_SERV_PAYMENTS=ROOT_URL + "finance/approve_serv_payments.php";
    public static final String URL_APPROVED_SERV_PAYMENTS=ROOT_URL + "finance/approved_serv_payments.php";
    public static final String URL_SUPPLY_PAYMENTS=ROOT_URL + "finance/supply_payments.php";
    public static final String URL_SUPPLY_PAYMENTS2=ROOT_URL + "finance/supply_payments2.php";
    public static final String URL_PAY_SUPPLIER=ROOT_URL + "finance/pay_supplier.php";

    //shipping mrg
    public static final String URL_ORDERS_TO_SHIP=ROOT_URL + "ship_mrg/orders_to_ship.php";
    public static final String URL_GET_DRIVERS=ROOT_URL + "ship_mrg/get_drivers.php";
    public static final String URL_SHIP_ORDER=ROOT_URL + "ship_mrg/ship_order.php";
    public static final String URL_SHIPPING_ORDERS=ROOT_URL + "ship_mrg/shipping_orders.php";
    public static final String URL_APPROVE_TENDER=ROOT_URL + "ship_mrg/approve_tender.php";


    //Service   Manager
    public static final String URL_QUOTATION_REQUEST=ROOT_URL + "doctor/new_surrogates.php";
    public static final String URL_NEW_DONORS=ROOT_URL + "doctor/new_donors.php";
    public static final String URL_GET_TECHNICIANS=ROOT_URL + "serv_mrg/get_technicians.php";
    public static final String URL_ASSIGN_TECH=ROOT_URL + "serv_mrg/assign_tech.php";

    //technician
    public static final String URL_GET_ASSIGNED_SITES=ROOT_URL + "technician/assigned_orders.php";
    public static final String URL_GET_ASSIGNED_SERVICES=ROOT_URL + "technician/assigned_services.php";
    public static final String URL_SEND_QUOTATION=ROOT_URL + "technician/send_quotation.php";
    public static final String URL_CONFIRM_COMPLETION=ROOT_URL + "technician/confirm_completion.php";

    // Driver
    public static final String URL_MARK_ORDER=ROOT_URL + "driver/mark_arrived.php";
    //Store mrg
    public static final String URL_GET_STOCK=ROOT_URL + "stock_mrg/stock.php";
    public static final String URL_MEDICINE_STOCK=ROOT_URL + "stock_mrg/medicine_stock.php";
    public static final String URL_ADD_STOCK=ROOT_URL + "stock_mrg/add_stock.php";
    public static final String URL_SUPPLIER=ROOT_URL + "stock_mrg/suppliers.php";
    public static final String URL_SEND_REQUEST=ROOT_URL + "stock_mrg/send_requests.php";
    public static final String URL_REQUESTS=ROOT_URL + "stock_mrg/request.php";
    public static final String URL_REQUESTMATERIALS=ROOT_URL + "stock_mrg/material_request.php";
    public static final String URL_REQUESTED_EQUIPMENTS=ROOT_URL + "stock_mrg/equipment_request.php";
    public static final String URL_REQUESTED_MEDICINE=ROOT_URL + "stock_mrg/medicine_request.php";
    public static final String URL_APPROVE_MATERIALS=ROOT_URL + "ship_mrg/approve_materials.php";
    public static final String URL_APPROVE_EQUIPMENTS=ROOT_URL + "ship_mrg/approve_equipments.php";
    public static final String URL_APPROVE_MEDICINE=ROOT_URL + "ship_mrg/approve_medicine.php";
    //Doctor
    public static final String URL_GET_LAB_TECH=ROOT_URL + "doctor/get_lab_tech.php";
    public static final String URL_ASSIGN_LAB_TECH=ROOT_URL + "doctor/assign_lab_tech.php";
    public static final String URL_APPROVED_SURROGACY=ROOT_URL + "doctor/approved_surrogacy.php";
    public static final String URL_ASSIGN_MEDICAL_TEST=ROOT_URL + "doctor/assign_med_test.php";
    public static final String URL_PENDING_SURROGACY=ROOT_URL + "doctor/pending_surrogacy.php";
    public static final String URL_FINAL_PRESCRIPTION=ROOT_URL + "doctor/confirm_complete_test.php";
    public static final String URL_CHECKUPS=ROOT_URL + "doctor/checkups.php";
    public static final String URL_APPROVE_FIRST_CHECKUP=ROOT_URL + "doctor/approve_first_check.php";
    public static final String URL_APPROVE_SECOND_CHECKUP=ROOT_URL + "doctor/approve_second_check.php";
    public static final String URL_APPROVE_THIRD_CHECKUP=ROOT_URL + "doctor/approve_third_check.php";
    public static final String URL_GET_FIRST_CHECK=ROOT_URL + "doctor/get_first_check.php";
    public static final String URL_GET_SECOND_CHECK=ROOT_URL + "doctor/get_second_check.php";
    public static final String URL_GET_THIRD_CHECK=ROOT_URL + "doctor/get_third_check.php";
    public static final String URL_GET_PRESCRIPTIONS=ROOT_URL + "doctor/prescriptions.php";

    //Surrogate Mother
    public static final String URL_MY_APPLICATION= ROOT_URL+"client/my_application.php";
    public static final String URL_SCHEDULED_CHECKUP= ROOT_URL+"surrogate_mother/checkups.php";
    //Donor
    public static final String URL_TRACK_APPLICATION= ROOT_URL+"donor/my_application.php";
    //Lab Tech
    public static final String URL_ASSIGNED_MED_TEST=ROOT_URL + "lab_tech/assigned_medical.php";
   public static final String URL_DONOR_TEST=ROOT_URL + "lab_tech/donor_test.php";
    public static final String URL_APPROVE_MEDICAL=ROOT_URL + "lab_tech/approve_medical.php";
    public static final String URL_ASSIGNED_FERTILITY_TEST=ROOT_URL + "lab_tech/fertility_test.php";
    public static final String URL_GET_PARENT=ROOT_URL + "lab_tech/get_parent.php";
    public static final String URL_GET_SURROGATE=ROOT_URL + "lab_tech/get_surrogate.php";
    public static final String URL_GET_PARTNER=ROOT_URL + "lab_tech/get_partner.php";
    public static final String URL_APPROVE_FERTILITY=ROOT_URL + "lab_tech/approve_fertility.php";
    public static final String URL_REQUEST_EQUIPMENTS=ROOT_URL + "lab_tech/request_equipment.php";
    public static final String URL_REQUEST_EQUIPMENT=ROOT_URL + "lab_tech/request_items.php";
    //intended parents
   public static final String URL_BOOK_APPOINTMENT = ROOT_URL+"client/book_appointment.php";
   public static final String URL_MY_BOOKINGS= ROOT_URL+"intended_parent/my_booking.php";
   public static final String URL_GET_ATTORNEY=ROOT_URL + "intended_parent/get_attorney.php";
   public static final String URL_ASSIGN_ATTORNEY=ROOT_URL + "intended_parent/select_attorney.php";
   public static final String URL_GET_AGREEMENTS = ROOT_URL + "intended_parent/get_agreement.php/";
   public static final String URL_MARK_SERVICE_COMPLETE=ROOT_URL + "intended_parent/confirm_complete.php";
   public static final String URL_GET_INVOICE= ROOT_URL+"intended_parent/invoice_history.php";
   public static final String URL_TRACK_CHECKUP= ROOT_URL+"intended_parent/track_checkups.php";
   //Attorney
   public static final String URL_NEW_APPOINTMENTS=ROOT_URL + "attorney/new_appointments.php";
   public static final String URL_UPLOAD_AGREEMENT = ROOT_URL + "attorney/upload_agreement.php/";
   //Pharmacist
   public static final String URL_NEW_PRESCRIPTION=ROOT_URL + "pharmacist/new_prescription.php";
   public static final String URL_CONFIRM_DISPENSE=ROOT_URL + "pharmacist/confirm_dispense.php";
   public static final String URL_REQUEST_MEDICINE=ROOT_URL + "pharmacist/request_medicine.php";

}
