package com.eklinik.e_klinikappnew.data.models

data class ErrorResponse(
    val message: String
)
data class LoginRequest(
    val nationalId: String,
    val password: String
)
data class LoginResponse(
    val accessToken: String,
    val tokenType: String,
    val role: String?
)

// Profile API Response Models
data class UserResponse(
    val id: Int,
    val nationalId: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val role: String,
    val createdAt: String,
    val deleted: Boolean,
    val patientProfile: PatientProfile?,
    val doctorInfo: DoctorInfo?
)

data class PatientProfile(
    val dateOfBirth: String?,
    val weight: Double?,
    val height: Double?,
    val hasChronicIllness: Boolean,
    val isMedicationDependent: Boolean,
    val birthPlaceCity: String?,
    val birthPlaceDistrict: String?,
    val address: String?,
    val country: String?
)

data class DoctorInfo(
    val title: String,
    val clinicName: String
)

// Register Request Models
data class RegisterRequest(
    val nationalId: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val phoneNumber: String
)

data class PatientProfileRequest(
    val birthDate: String? = null,
    val gender: String? = null,
    val address: String? = null,
    val emergencyContactName: String? = null,
    val emergencyContactPhone: String? = null
)

data class RegisterPatientCombinatedRequest(
    val userRequest: RegisterRequest,
    val profileRequest: PatientProfileRequest? = null
)