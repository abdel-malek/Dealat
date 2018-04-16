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
    
    var msg : String = ""

    var adDetailsVC : AdDetailsVC!

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = self.ad.title
        configureNavigationBar()
        
        Provider.setScreenName("Ad details")

    }
    
    func configureNavigationBar() {
        //setup back button
        self.navigationItem.hidesBackButton = false
        let rr = UIBarButtonItem(title: "", style: UIBarButtonItemStyle.plain, target: nil, action:nil)
        self.navigationItem.backBarButtonItem = rr
    }
    
    @IBAction func unwindToVC1(segue:UIStoryboardSegue) {
        print("unwindToVC1")
        
        self.showErrorMessage(text: self.msg)
        self.adDetailsVC.refreshData()
        self.adDetailsVC.getData()
    }

    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let i = segue.destination as? AdDetailsVC{
            i.ad = self.ad
            i.parentBase = self
            self.adDetailsVC = i

            self.addChildViewController(i)
        }
    }
    
    @IBAction func callAction(){
        if User.isRegistered(){
            print("--\(self.ad.seller_phone)")
            if let phone  = self.ad.seller_phone, let url = URL(string: "telprompt:\(phone)") {
                if UIApplication.shared.canOpenURL(url) {
                    UIApplication.shared.openURL(url)
                }
            }
        }else{
            self.showErrorMessage(text: "need_register".localized)
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
        let alert = UIAlertController.init(title: "Alert!".localized, message: "removeAdd".localized, preferredStyle: UIAlertControllerStyle.alert)
        
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
        if User.isRegistered(){
            let same = self.ad.seller_id.intValue == User.getID()
            
            self.editBtn.isHidden = !same
            self.deleteBtn.isHidden = !same
            self.callBtn.isHidden = same
            self.messageBtn.isHidden = same
            //        self.reportBtn.isHidden = same
        }else{
            self.callBtn.isHidden = false
            self.messageBtn.isHidden = false
        }
    }
    
    func showErrorMessage(text: String,duration : TimeInterval = 3) {
        KSToastView.ks_showToast(text, duration: duration)
    }
    
}
