//
//  AdDetailsBaseVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/12/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import KSToastView

class AdDetailsBaseVC: UIViewController {
    
    var ad : AD!
    var adBase : AD!

    var tamplateId : Int = -1
    
    // BottomBar
    @IBOutlet weak var callBtn : UIButton!
    @IBOutlet weak var messageBtn : UIButton!
    @IBOutlet weak var reportBtn : UIButton!
    @IBOutlet weak var editBtn : UIButton!
    @IBOutlet weak var deleteBtn : UIButton!
    
    @IBOutlet weak var sellerLbl : UILabel!
    
    @IBOutlet weak var sallerVVHeight : NSLayoutConstraint!
    @IBOutlet weak var contactVVHeight : NSLayoutConstraint!

    
    var msg : String = ""

    var adDetailsVC : AdDetailsVC!

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = self.ad.title.emojiUnescapedString
        configureNavigationBar()
        
        self.sallerVVHeight.constant = 0
        self.contactVVHeight.constant = 0

        
        Provider.setScreenName("AdDetailsActivity")

    }
    
    func configureNavigationBar() {
        //setup back button
        self.navigationItem.hidesBackButton = false
        let rr = UIBarButtonItem(title: "", style: UIBarButtonItem.Style.plain, target: nil, action:nil)
        self.navigationItem.backBarButtonItem = rr
    }
    
    @IBAction func unwindToVC1(segue:UIStoryboardSegue) {
        print("unwindToVC1")
        
        self.showErrorMessage(text: "Changes Saved".localized)        
        
        self.adDetailsVC.refreshData()
        self.adDetailsVC.getData()
    }

    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let i = segue.destination as? AdDetailsVC{
            i.ad = self.ad
            i.parentBase = self
            self.adDetailsVC = i

            self.addChild(i)
        }
    }
    
    @IBAction func callAction(){
        if User.isRegistered(){
            
            
            if let phone  = self.ad.seller_phone  {
                
                self.tellPhone(phone,withAlert: true)

                /*self.ad.whatsup_number = phone
                
                if let whatsapp = self.ad.whatsup_number{
                    let alert = UIAlertController.init(title: nil, message: nil, preferredStyle: .actionSheet)
                    
                    alert.addAction(UIAlertAction.init(title: "Call".localized, style: .default, handler: { (ac) in
                        self.tellPhone(whatsapp,withAlert : false)
                    }))
                    
                    alert.addAction(UIAlertAction.init(title: "Whatsapp".localized, style: .default, handler: { (ac) in
                        self.whatsappPhone(whatsapp)
                    }))
                    
                    alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
                    
                    self.present(alert, animated: true, completion: nil)
                }
                    
                else{
                    self.tellPhone(phone,withAlert: true)
                }*/
            }
            
            
        }else{
            self.showErrorMessage(text: "need_register".localized)
        }
    }
    
    func tellPhone(_ p : String, withAlert : Bool = false){
        let urlString = withAlert ? "telprompt://" : "tel://"
        
        var phone = p
        if phone.first == "0"{
            _ = phone.dropFirst()
        }
        if phone.count == 9{
            phone = "0" + phone
        }

        
        if let url = URL(string: "\(urlString)\(phone)"){
            if UIApplication.shared.canOpenURL(url) {
                UIApplication.shared.openURL(url)
            }
        }
    }
    
    func whatsappPhone(_ p : String){
        
        var phone = p
        
        if phone.first == "0"{
            _ = phone.dropFirst()
        }
        if phone.count == 9{
            phone = "963" + p
        }
        
        if let url = URL(string: "whatsapp://send?phone=\(phone)"){
            if UIApplication.shared.canOpenURL(url) {
                UIApplication.shared.openURL(url)
            }
        }
    }
    
    
    
    
    @IBAction func openChat(){
        if User.isRegistered(){
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "ChatDetailsVC") as! ChatDetailsVC
            let chat = Chat()
            chat.ad_title = self.ad.title
            chat.ad_id = self.ad.ad_id
            chat.seller_id = self.ad.seller_id
            chat.seller_name = self.ad.seller_name
            vc.chat = chat
            vc.ad = self.ad
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            self.showErrorMessage(text: "need_register".localized)
        }
    }
    
    @IBAction func editAction(){
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "NewAddBaesVC") as! NewAddBaesVC
        vc.ad = self.adBase
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @IBAction func removeAction(){
        let alert = UIAlertController.init(title: "Alert!".localized, message: "removeAdd".localized, preferredStyle: UIAlertController.Style.alert)
        
        alert.addAction(UIAlertAction.init(title: "OK".localized, style: .default, handler: { (ac) in
            self.adDetailsVC.showLoading()
            Communication.shared.change_status(ad_id: self.ad.ad_id.intValue, status: 6, callback: { (res) in
                self.adDetailsVC.getRefreshing()
            })
        }))
        
        alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
        
        self.present(alert, animated: true, completion: nil)
    }

    
    
    func refreshBar(){
        self.sallerVVHeight.constant = 50
        self.contactVVHeight.constant = 50

        
        if User.isRegistered(){
            let same = self.ad.seller_id.intValue == User.getID()

            self.editBtn.isHidden = !same
            self.deleteBtn.isHidden = !same
            
            self.callBtn.isHidden = same
            self.messageBtn.isHidden = same
            
            if !same, let v = self.ad.ad_visible_phone{
                self.callBtn.isHidden = !v.Boolean
            }
        }else{
            self.callBtn.isHidden = false
            self.messageBtn.isHidden = false
            
            if let v = self.ad.ad_visible_phone{
                self.callBtn.isHidden = !v.Boolean
            }
        }
        
        if self.ad.is_admin.Boolean{
            self.sallerVVHeight.constant = 0
            self.messageBtn.isHidden = true
            
            var same = false
            
            if User.isRegistered(){
                same = self.ad.seller_id.intValue == User.getID()
            }
            
            if !same && self.messageBtn.isHidden && self.callBtn.isHidden{
                self.contactVVHeight.constant = 0
            }
            
        }
        
    }
    
    func showErrorMessage(text: String,duration : TimeInterval = 3) {
        KSToastView.ks_showToast(text, duration: duration)
    }
    
}
