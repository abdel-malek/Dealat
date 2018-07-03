//
//  NewAddBaesVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class NewAddBaesVC: UIViewController {

    @IBOutlet weak var submitBtn : UIButton!
    
    var homeVC : HomeVC!
    var embeddedViewController : NewAddVC!
    var ad  : AD!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        
        if ad != nil{
            Provider.setScreenName("EditAdActivity")
        }else{
            Provider.setScreenName("SubmitAdActivity")
        }
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let i = segue.destination as? NewAddVC{
            embeddedViewController = i
            i.ad = self.ad
            i.homeVC = self.homeVC
            self.addChildViewController(i)

            if i.editMode{
                self.title = "SellEdit".localized
                self.submitBtn.setTitle("SubmitEdit".localized, for: .normal)
            }else{
                self.title = "Sell".localized
                self.submitBtn.setTitle("Submit".localized, for: .normal)
            }
        }
    }
    
    @objc func reset(){
        self.embeddedViewController.reset()
    }

    @IBAction func submitAction(_ sender: UIButton) {
        self.embeddedViewController.submitAction()
    }
    
}
