//
//  SideMenuVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/6/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import FirebaseMessaging

class SideMenuVC: BaseVC {
    
    @IBOutlet weak var img : UIImageView!
    @IBOutlet var btns: [UIButton]!
    @IBOutlet weak var regVV: UIView!

    @IBOutlet weak var nameLbl: UILabel!

    
    var homeVC : HomeVC!
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        
        refreshImg()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        if let i = self.btns.first(where: {$0.tag == -1}){
            let tit = User.isRegistered() ? "Logout".localized : "Register".localized
            i.setTitle(tit, for: UIControl.State.normal)
        }

        

        if User.isRegistered(){
            
            self.regVV.isHidden = true

        }else{
            for i in btns{
                if (i.tag > 0  && i.tag < 7) || (i.tag ==  10) || (i.tag ==  11) {
                    i.isHidden = true
                }
            }
        }
        
    }
    
    override func setupViews() {
        DispatchQueue.main.async {
            self.img.layer.cornerRadius = self.img.bounds.width / 2
        }
        
        for i in btns{
            i.addTarget(self, action: #selector(self.didSelect), for: .touchUpInside)
        }
    }
    
    @objc @IBAction func didSelect(_ i : UIButton){
//        refreshBtns()
        
        if i.tag != -1{
            UIView.animate(withDuration: 0.2) {
                i.backgroundColor = Theme.Color.red
                i.setTitleColor(Theme.Color.White, for: .normal)
            }
            UIView.animate(withDuration: 0.2) {
                i.backgroundColor = .clear
                i.setTitleColor(Theme.Color.darkGrey, for: .normal)
            }
        }

        
        self.dismiss(animated: true) {
            
            if i.tag == -1{
                if User.isRegistered(){
                
                    self.logoutAction()
                }else{
                    AppDelegate.setupViews()
                }
            }
            
            if i.tag == 0{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
                self.homeVC.navigationController?.pushViewController(vc, animated: false)
            }

            
            if i.tag == 1{
                if User.isRegistered(){
                    let vc = self.storyboard?.instantiateViewController(withIdentifier: "MyProfileVC") as! MyProfileVC
                    self.homeVC.navigationController?.pushViewController(vc, animated: true)
                }
            }
            
            if i.tag == 2{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "MyProfileVC") as! MyProfileVC
                vc.currentPage = 0
                self.homeVC.navigationController?.pushViewController(vc, animated: true)
            }
            if i.tag == 3{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "MyProfileVC") as! MyProfileVC
                vc.currentPage = 1
                self.homeVC.navigationController?.pushViewController(vc, animated: true)
            }
            if i.tag == 4{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "MyProfileVC") as! MyProfileVC
                vc.currentPage = 2
                self.homeVC.navigationController?.pushViewController(vc, animated: true)
            }
            if i.tag == 5{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "EditProfileVC") as! EditProfileVC
                vc.homeVC = self.homeVC
                self.homeVC.navigationController?.pushViewController(vc, animated: true)
            }
            if i.tag == 6{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "SavedSearchesVC") as! SavedSearchesVC
                vc.homeVC = self.homeVC
                self.homeVC.navigationController?.pushViewController(vc, animated: true)
            }

            if i.tag == 9{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "AboutVC") as! AboutVC
//                vc.homeVC = self.homeVC
                self.homeVC.navigationController?.pushViewController(vc, animated: true)
            }
            
            if i.tag == 10{
                self.logoutAction()
            }
            
            if i.tag == 11{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "ScanQRCodeVC") as! ScanQRCodeVC
                vc.homeVC = self.homeVC
                self.homeVC.navigationController?.pushViewController(vc, animated: true)
            }

            
            
            // CHANGE LANGUAGE
            if i.tag == 7{
                /*if AppDelegate.isArabic(){
                 UserDefaults.standard.setValue(["en"], forKey: "AppleLanguages")
                 }else{
                 UserDefaults.standard.setValue(["ar"], forKey: "AppleLanguages")
                 }
                 UserDefaults.standard.synchronize()*/
                
                let alert = UIAlertController.init(title: "ChangeLangTitle".localized, message: "ChangeLangMessage".localized, preferredStyle: .alert)
                
                if AppDelegate.isArabic(){
                    let ac = UIAlertAction.init(title: "AR".localized, style: .default, handler: nil)
                    ac.setValue(#imageLiteral(resourceName: "checkmark"), forKey: "image")
                    alert.addAction(ac)
                    
                    alert.addAction(UIAlertAction.init(title: "EN".localized, style: .default, handler: { (ac) in
                        UserDefaults.standard.setValue(["en"], forKey: "AppleLanguages")
                        UserDefaults.standard.synchronize()
                        self.restartHome()
                    }))

                }else{
                    alert.addAction(UIAlertAction.init(title: "AR".localized, style: .default, handler: { (ac) in
                        UserDefaults.standard.setValue(["ar"], forKey: "AppleLanguages")
                        UserDefaults.standard.synchronize()
                        self.restartHome()
                    }))
                    
                    let ac = UIAlertAction.init(title: "EN".localized, style: .default, handler: nil)
                    ac.setValue(#imageLiteral(resourceName: "checkmark"), forKey: "image")
                    alert.addAction(ac)
                }
                
                alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
                
                
                self.homeVC.present(alert, animated: true, completion: nil)
            }
        }
    }
    
    
    func logoutAction(){
        let alert = UIAlertController.init(title: "Alert!".localized, message: "logoutMessage".localized, preferredStyle: .alert)
        
        alert.addAction(UIAlertAction.init(title: "OK".localized, style: .default, handler: { (ac) in
            
            self.homeVC.showLoading()
            Communication.shared.logout({ (res) in
                self.homeVC.hideLoading()
                
                User.clearMe()
                AppDelegate.setupViews()
                
            })
            
        }))
        alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
        
        self.homeVC.present(alert, animated: true, completion: nil)
    }
    
    func refreshImg(){
        let me = User.getCurrentUser()
        
        if User.isRegistered(){
            if let path = me.personal_image{
                Provider.sd_setImage(self.img, urlString: path)
            }
            
            self.nameLbl.text = me.name
        }else{
            self.nameLbl.text = nil
        }
    }
    
    func restartHome(){
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
        let nv = UINavigationController.init(rootViewController: vc)
        
        if User.isRegistered(), let refreshedToken = Messaging.messaging().fcmToken {
            print("TOKEEEN : \(refreshedToken)")
            self.homeVC.showLoading()
            Communication.shared.save_user_token(refreshedToken) { (res) in
                self.homeVC.hideLoading()
                
                appDelegate.window?.rootViewController = nv
            }
        }else{
            appDelegate.window?.rootViewController = nv
        }

        
    }
    
    
    func refreshBtns(){
        for i in btns{
            i.backgroundColor = .clear
            i.setTitleColor(Theme.Color.darkGrey, for: .normal)
        }
    }
    
    
    
    
}
