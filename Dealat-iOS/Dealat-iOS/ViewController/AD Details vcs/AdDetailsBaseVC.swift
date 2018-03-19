//
//  AdDetailsBaseVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/12/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class AdDetailsBaseVC: UIViewController {

    var ad : AD!
    var tamplateId : Int = -1
    
    // BottomBar
    @IBOutlet weak var callBtn : UIButton!
    @IBOutlet weak var messageBtn : UIButton!
    @IBOutlet weak var reportBtn : UIButton!
    @IBOutlet weak var editBtn : UIButton!


    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = self.ad.title
        
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let i = segue.destination as? AdDetailsVC{
            i.ad = self.ad
            i.parentBase = self
//            i.tamplateId = self.tamplateId
            
//            var cat = ""
//            cat += (ad.parent_category_name != nil) ? "\(ad.parent_category_name!)-" : ""
//            cat += (ad.category_name != nil) ? "\(ad.category_name!)" : ""
//            i.category_full_name = "\(cat)"
            
            self.addChildViewController(i)
        }
    }
    
    @IBAction func callAction(){
        print("--\(self.ad.seller_phone)")
        if let phone  = self.ad.seller_phone, let url = URL(string: "telprompt:\(phone)") {
            if UIApplication.shared.canOpenURL(url) {
                UIApplication.shared.openURL(url)
            }
        }
    }
    
    @IBAction func openChat(){
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "ChatDetailsVC") as! ChatDetailsVC
        let chat = Chat()
        chat.ad_title = self.ad.title
        chat.ad_id = self.ad.ad_id
        chat.seller_id = self.ad.seller_id
        vc.chat = chat
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @IBAction func editAction(){

    }

    
    func refreshBar(){
        if User.isRegistered(){
            let same = self.ad.seller_id.intValue == User.getID()
            
            self.editBtn.isHidden = !same
            self.callBtn.isHidden = same
            self.messageBtn.isHidden = same
            //        self.reportBtn.isHidden = same
        }
    }

}
