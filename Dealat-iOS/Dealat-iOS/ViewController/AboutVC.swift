//
//  AboutVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 4/19/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import  SwiftyJSON
import MessageUI
import AttributedTextView

class AboutVC: BaseVC,MFMailComposeViewControllerDelegate {
    
    @IBOutlet weak var scrollView : UIScrollView!
    @IBOutlet weak var facebookBtn : UIButton!
    @IBOutlet weak var instagramBtn : UIButton!
    @IBOutlet weak var twitterBtn : UIButton!
    @IBOutlet weak var youtubeBtn : UIButton!
    @IBOutlet weak var linkedinBtn : UIButton!
    @IBOutlet weak var versionLbl : UILabel!

    
    @IBOutlet weak var emailBtn : UIButton!
    @IBOutlet weak var phoneLbl : UITextView!

//    @IBOutlet weak var phoneBtn : UIButton!
    
    @IBOutlet weak var textView : UITextView!
    @IBOutlet weak var img : UIImageView!

    
    var aboutInfo : AboutInfo!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.img.image = Provider.logoImage

        self.scrollView.isHidden = true

        self.getData()
        self.title = "Help".localized
        
        
        if let version = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String {
            versionLbl.text = version
        }else{
            versionLbl.text = nil
        }

        Provider.setScreenName("AboutActivity")
    }
    
    
    override func getRefreshing() {
        Communication.shared.get_about_info { (res) in
            self.hideLoading()
            
            self.aboutInfo = res
            
            if let email = res.email, !email.isEmpty{
                self.emailBtn.setTitle(email, for: .normal)
            }else{
                self.emailBtn.isHidden = true
            }
            
            let attPhone = NSMutableAttributedString.init(attributedString: res.phone.html2AttributedString!)
            attPhone.addAttributes([NSAttributedStringKey.foregroundColor: UIColor.white,NSAttributedStringKey.font: Theme.Font.Calibri.withSize(17)], range: NSRange(location: 0, length: attPhone.length))
            self.phoneLbl.attributedText = attPhone


            let attAbout = NSMutableAttributedString.init(attributedString: res.about_us.html2AttributedString!)
            attAbout.addAttributes([NSAttributedStringKey.foregroundColor: UIColor.white,NSAttributedStringKey.font: Theme.Font.Calibri.withSize(17)], range: NSRange(location: 0, length: attAbout.length))
            self.textView.attributedText = attAbout
            
            
//            self.textView.allowsEditingTextAttributes = true
            

//            if let phone = res.phone, !phone.isEmpty{
//                self.phoneBtn.setTitle(phone, for: .normal)
//            }else{
//                self.phoneBtn.isHidden = true
//            }
            if res.facebook_link == nil || res.facebook_link.isEmpty{
                self.facebookBtn.isHidden = true
            }
            
            if res.instagram_link == nil || res.instagram_link.isEmpty{
                self.instagramBtn.isHidden = true
            }
            if res.twiter_link == nil || res.twiter_link.isEmpty{
                self.twitterBtn.isHidden = true
            }
            if res.linkedin_link == nil || res.linkedin_link.isEmpty{
                self.linkedinBtn.isHidden = true
            }
            if res.youtube_link == nil || res.youtube_link.isEmpty{
                self.youtubeBtn.isHidden = true
            }
            
            self.scrollView.isHidden = false
        }
    }
    
    func mailComposeController(_ controller: MFMailComposeViewController, didFinishWith result: MFMailComposeResult, error: Error?) {
        controller.dismiss(animated: true, completion: nil)
    }
    
   @IBAction  func sendMail(){
        let mail = MFMailComposeViewController()
        mail.mailComposeDelegate = self
        mail.setToRecipients([self.aboutInfo.email])
        //            mail.setMessageBody("<p>You're so awesome!</p>", isHTML: true)
    
        if MFMailComposeViewController.canSendMail(){
            self.present(mail, animated: true, completion: nil)
        }
    }
    
    @IBAction func callPhone(){
        var phone = self.aboutInfo.phone!
        if phone.first == "0"{
            _ = phone.dropFirst()
        }
        if phone.count == 9{
            phone = "0" + phone
        }
        
        if let url = URL.init(string: "telprompt://\(phone)"){
            if UIApplication.shared.canOpenURL(url){
                UIApplication.shared.openURL(url)
            }
        }
    }
    
    @IBAction func facebookAction(){
        if let url = URL.init(string: self.aboutInfo.facebook_link){
            if UIApplication.shared.canOpenURL(url){
                UIApplication.shared.openURL(url)
            }
        }
    }
    
    @IBAction func instagramAction(){
        if let url = URL.init(string: self.aboutInfo.instagram_link){
            if UIApplication.shared.canOpenURL(url){
                UIApplication.shared.openURL(url)
            }
        }
    }
    @IBAction func twitterAction(){
        if let url = URL.init(string: self.aboutInfo.twiter_link){
            if UIApplication.shared.canOpenURL(url){
                UIApplication.shared.openURL(url)
            }
        }
    }
    @IBAction func youtubeAction(){
        if let url = URL.init(string: self.aboutInfo.youtube_link){
            if UIApplication.shared.canOpenURL(url){
                UIApplication.shared.openURL(url)
            }
        }
    }
    @IBAction func linkedinAction(){
        if let url = URL.init(string: self.aboutInfo.linkedin_link){
            if UIApplication.shared.canOpenURL(url){
                UIApplication.shared.openURL(url)
            }
        }
    }
}
