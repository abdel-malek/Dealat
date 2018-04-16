//
//  FilterBaseVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/19/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class FilterBaseVC: BaseVC {
    
    weak var embeddedViewController : FilterVC!
    weak var adsList : AdsListVC!
    weak var homeVC : HomeVC!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "Filter".localized
        
        self.navigationItem.leftBarButtonItem = UIBarButtonItem.init(barButtonSystemItem: .stop, target: self, action: #selector(self.dis))
        
        self.navigationItem.rightBarButtonItem = UIBarButtonItem.init(barButtonSystemItem: .refresh, target: self, action: #selector(self.resetFilters))
        
        Provider.setScreenName("Filter")

        
    }
    
    @objc func dis(){
        self.dismiss(animated: true, completion: nil)
    }
    
    
    @objc func resetFilters(){
        //        Provider.selectedLocation = nil
        //        Provider.selectedCategory = nil
        
        //        self.embeddedViewController.selectedLocation = nil
        //        self.embeddedViewController.selectedCategory = nil
        
        self.embeddedViewController.filter = FilterParams()
        
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "vc"{
            if segue.destination.isKind(of: FilterVC.self){
                self.embeddedViewController = segue.destination as! FilterVC
                self.addChildViewController(self.embeddedViewController)
            }
        }
    }
    
    @IBAction func showAction(){
        
        self.dismiss(animated: true) {
            Provider.filter = self.embeddedViewController.filter
            // IMP
            self.embeddedViewController.refreshData()//getRefreshing()

            if self.adsList != nil{
                self.adsList.fromFilter = true
                self.adsList.getData()
            }
                
            else if self.homeVC != nil{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdsListVC") as! AdsListVC
                vc.fromFilter = true
                self.homeVC.navigationController?.pushViewController(vc, animated: true)
            }
        }
        
    }
    
}
