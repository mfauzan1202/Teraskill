package id.co.mka.teraskill.di

import id.co.mka.teraskill.ui.applying.upload_cv.UploadCVViewModel
import id.co.mka.teraskill.ui.auth.forgot_pass.ForgotPasswordViewModel
import id.co.mka.teraskill.ui.auth.forgot_pass.ResetPasswordViewModel
import id.co.mka.teraskill.ui.auth.otp.VerificationOTPViewModel
import id.co.mka.teraskill.ui.auth.signin.SignInViewModel
import id.co.mka.teraskill.ui.auth.signup.SignUpViewModel
import id.co.mka.teraskill.ui.checkout_class.UploadReceiptViewModel
import id.co.mka.teraskill.ui.classroom.class_certificate.CertificateViewModel
import id.co.mka.teraskill.ui.classroom.class_exam.ClassExamViewModel
import id.co.mka.teraskill.ui.classroom.class_preview.ClassPreviewViewModel
import id.co.mka.teraskill.ui.classroom.class_project.ClassProjectViewModel
import id.co.mka.teraskill.ui.classroom.class_theory.ClassSubChapterViewModel
import id.co.mka.teraskill.ui.learning_path.LearningPathViewModel
import id.co.mka.teraskill.ui.main.dashboard.class_progress.ClassProgressViewModel
import id.co.mka.teraskill.ui.main.dashboard.my_class.MyClassViewModel
import id.co.mka.teraskill.ui.main.dashboard.portfolio.add_portfolio.AddPortfolioViewModel
import id.co.mka.teraskill.ui.main.dashboard.portfolio.list_portfolio.ListPortfolioViewModel
import id.co.mka.teraskill.ui.main.home.HomeViewModel
import id.co.mka.teraskill.ui.main.settings.editprofile.EditProfileViewModel
import id.co.mka.teraskill.ui.main.settings.transaction_history.TransactionHistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    /** applying ViewModel */
    viewModel { UploadCVViewModel(get()) }

    /** Auth ViewModel */
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { VerificationOTPViewModel(get()) }
    viewModel { ResetPasswordViewModel(get()) }

    /** checkout_class ViewModel */
    viewModel { UploadReceiptViewModel(get()) }

    /** Classroom/class_exam ViewModel */
    viewModel { ClassExamViewModel(get()) }
    /** Classroom/class_preview ViewModel */
    viewModel { ClassPreviewViewModel(get()) }
    /** Classroom/class_submission ViewModel */
    viewModel { ClassProjectViewModel(get()) }
    /** Classroom/class_theory ViewModel */
    viewModel { ClassSubChapterViewModel(get()) }
//    viewModel { CertificateViewModel(get(), get()) }

    /** learning_path ViewModel */
    viewModel { LearningPathViewModel(get(), get()) }

    /** main/dashboard ViewModel */
    viewModel { ClassProgressViewModel(get()) }
    viewModel { MyClassViewModel(get()) }
    viewModel { ListPortfolioViewModel(get()) }
    viewModel { AddPortfolioViewModel(get()) }

    /** main/home ViewModel */
    viewModel { HomeViewModel(get()) }

    /** main/settings ViewModel */
    viewModel { EditProfileViewModel(get()) }
    viewModel { TransactionHistoryViewModel(get()) }


}
