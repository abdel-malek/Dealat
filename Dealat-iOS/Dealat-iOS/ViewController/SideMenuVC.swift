//
//  SideMenuVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/6/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class SideMenuVC: BaseVC {
    
    @IBOutlet weak var img : UIImageView!
    @IBOutlet var btns: [UIButton]!
    
    var homeVC : HomeVC!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
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
        
        UIView.animate(withDuration: 0.2) {
            i.backgroundColor = Theme.Color.red
            i.setTitleColor(Theme.Color.White, for: .normal)
        }
        
        UIView.animate(withDuration: 0.2) {
            i.backgroundColor = .clear
            i.setTitleColor(Theme.Color.darkGrey, for: .normal)
        }

        
        self.dismiss(animated: true) {
            
            // CHANGE LANGUAGE
            if i.tag == 6{
                /*if AppDelegate.isArabic(){
                 UserDefaults.standard.setValue(["en"], forKey: "AppleLanguages")
                 }else{
                 UserDefaults.standard.setValue(["ar"], forKey: "AppleLanguages")
                 }
                 UserDefaults.standard.synchronize()*/
                
                let alert = UIAlertController.init(title: "ChangeLangTitle".localized, message: "ChangeLangMessage".localized, preferredStyle: .actionSheet)
                
                if AppDelegate.isArabic(){
                    alert.addAction(UIAlertAction.init(title: "EN".localized, style: .default, handler: { (ac) in
                        UserDefaults.standard.setValue(["en"], forKey: "AppleLanguages")
                        UserDefaults.standard.synchronize()
                        self.restartHome()
                    }))
                    
                    let ac = UIAlertAction.init(title: "AR".localized, style: .default, handler: nil)
                    ac.setValue(#imageLiteral(resourceName: "checkmark"), forKey: "image")
                    alert.addAction(ac)
                }else{
                    let ac = UIAlertAction.init(title: "EN".localized, style: .default, handler: nil)
                    ac.setValue(#imageLiteral(resourceName: "checkmark"), forKey: "image")
                    alert.addAction(ac)
                    
                    alert.addAction(UIAlertAction.init(title: "AR".localized, style: .default, handler: { (ac) in
                        UserDefaults.standard.setValue(["ar"], forKey: "AppleLanguages")
                        UserDefaults.standard.synchronize()
                        self.restartHome()
                    }))
                }
                
                alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
                
                self.homeVC.present(alert, animated: true, completion: nil)
            }
        }
    }
    
    func restartHome(){
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
        vc.isChangeLanguage = true
        let nv = UINavigationController.init(rootViewController: vc)
        
        appDelegate.window?.rootViewController = nv
    }
    
    
    func refreshBtns(){
        for i in btns{
            i.backgroundColor = .clear
            i.setTitleColor(Theme.Color.darkGrey, for: .normal)
        }
    }
    
    
}
