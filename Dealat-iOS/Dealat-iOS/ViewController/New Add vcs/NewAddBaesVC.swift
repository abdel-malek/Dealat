//
//  NewAddBaesVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class NewAddBaesVC: UIViewController {

    var embeddedViewController : NewAddVC!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.navigationItem.rightBarButtonItem = UIBarButtonItem.init(title: "Reset", style: .plain, target: self, action: #selector(self.reset))

        self.title = "Add new"
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let i = segue.destination as? NewAddVC{
            embeddedViewController = i
            self.addChildViewController(i)
        }
    }
    
    @objc func reset(){
        self.embeddedViewController.reset()
    }

    @IBAction func submitAction(_ sender: UIButton) {
        self.embeddedViewController.submitAction()
    }
    
}
